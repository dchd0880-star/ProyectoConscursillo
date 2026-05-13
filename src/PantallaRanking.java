import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PantallaRanking extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel modelo;

    public PantallaRanking() {
        setTitle("RANKING MUNDIAL - El Concursillo");
        // Importante: DISPOSE_ON_CLOSE para no cerrar todo el juego
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        setLocationRelativeTo(null); // Centra la ventana

        contentPane = new JPanel();
        contentPane.setBackground(new Color(95, 41, 160)); // Morado oscuro para que pegue con tus botones
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(0, 10));
        setContentPane(contentPane);

        // 1. Configurar el modelo de la tabla
        // Usamos los nombres de columna que tú quieras mostrar
        String[] columnas = {"Posición", "Nombre Jugador", "Premio Conseguido", "Fecha"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La tabla no se puede editar a mano
            }
        };

        table = new JTable(modelo);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));

        // 2. Añadir scroll
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // 3. Botón para salir
        JButton btnCerrar = new JButton("VOLVER AL MENÚ");
        btnCerrar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCerrar.setBackground(Color.WHITE);
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnCerrar, BorderLayout.SOUTH);

        // 4. Cargar datos
        cargarDatosDesdeMongo();
    }

    private void cargarDatosDesdeMongo() {
        // Tu URI de conexión
        String URI = "mongodb+srv://dchd0880_db_user:BFyNiEnlQMgVeVtR@proyectoconcursillo.2z703nl.mongodb.net/";

        try (MongoClient mongoClient = MongoClients.create(URI)) {
            // USAMOS EL NOMBRE EXACTO: ProyectoConcursillo
            MongoDatabase database = mongoClient.getDatabase("ProyectoConcursillo");
            MongoCollection<Document> collection = database.getCollection("ranking");

            // Ordenamos por premio de mayor a menor (-1)
            FindIterable<Document> resultados = collection.find().sort(new Document("premio", -1));

            int posicion = 1;
            for (Document doc : resultados) {
                // Sacamos los datos. OJO: Revisa que en ConexionMongo uses estas mismas llaves ("jugador", "premio", "fecha")
                String nombre = doc.getString("jugador");
                
                // Usamos Object por si el premio se guardó como Integer o Long
                Object premio = doc.get("premio");
                
                // La fecha la sacamos como objeto y la pasamos a String
                Object fechaObj = doc.get("fecha");
                String fechaStr = (fechaObj != null) ? fechaObj.toString() : "---";

                // Añadimos la fila a la tabla
                modelo.addRow(new Object[] {
                    posicion + "º", 
                    nombre, 
                    premio + " €", 
                    fechaStr
                });
                posicion++;
            }
            
            if (posicion == 1) {
                JOptionPane.showMessageDialog(this, "Aún no hay partidas registradas en el ranking.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con el Ranking: " + e.getMessage());
            e.printStackTrace();
        }
    }
}