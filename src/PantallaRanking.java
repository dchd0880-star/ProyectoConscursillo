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

// Ventana que muestra la tabla de clasificación mundial recuperando los datos de MongoDB Atlas
public class PantallaRanking extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel modelo; // Modelo para gestionar los datos de la tabla

    // Constructor: Configura la interfaz visual de la tabla de puntuaciones
    public PantallaRanking() {
        setTitle("RANKING MUNDIAL - El Concursillo");
        
        // Importante: DISPOSE_ON_CLOSE asegura que al cerrar esta ventana no se cierre el programa completo
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla del usuario

        // Configuración del panel contenedor con un diseño de bordes (BorderLayout)
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 128)); // Azul oscuro para mantener la estética del juego
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(0, 10));
        setContentPane(contentPane);

        // --- 1. CONFIGURACIÓN DEL MODELO DE LA TABLA ---
        // Definimos las cabeceras de las columnas
        String[] columnas = {"Posición", "Nombre Jugador", "Premio Conseguido", "Fecha"};
        
        // Instanciamos el modelo de la tabla y desactivamos la edición directa de las celdas
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // El ranking es solo de lectura
            }
        };

        // Creamos la tabla con el modelo definido y ajustamos su aspecto
        table = new JTable(modelo);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(30); // Altura de las filas para una mejor legibilidad
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14)); // Fuente en negrita para la cabecera

        // --- 2. PANEL DE DESPLAZAMIENTO (SCROLL) ---
        // Envolvemos la tabla en un JScrollPane para que el usuario pueda navegar si hay muchos registros
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // --- 3. BOTÓN DE CIERRE ---
        JButton btnCerrar = new JButton("VOLVER AL MENÚ");
        btnCerrar.setForeground(new Color(255, 255, 255));
        btnCerrar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCerrar.setBackground(new Color(128, 128, 128));
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra únicamente esta ventana
            }
        });
        contentPane.add(btnCerrar, BorderLayout.SOUTH);

        // --- 4. CARGA DE DATOS ---
        // Al terminar de construir la interfaz, llamamos al método que conecta con la nube
        cargarDatosDesdeMongo();
    }

    // Método que conecta con Atlas, recupera las puntuaciones y las vuelca en la tabla
    private void cargarDatosDesdeMongo() {
        // URI de conexión al clúster remoto
        String URI = "mongodb+srv://dchd0880_db_user:BFyNiEnlQMgVeVtR@proyectoconcursillo.2z703nl.mongodb.net/";

        try (MongoClient mongoClient = MongoClients.create(URI)) {
            // Accedemos a la base de datos y a la colección de partidas guardadas
            MongoDatabase database = mongoClient.getDatabase("ProyectoConcursillo");
            MongoCollection<Document> collection = database.getCollection("ranking");

            // --- CONSULTA ORDENADA ---
            // Recuperamos todos los documentos y los ordenamos por el campo "premio" de mayor a menor (-1)
            FindIterable<Document> resultados = collection.find().sort(new Document("premio", -1));

            int posicion = 1;
            // Iteramos sobre los resultados obtenidos de la base de datos
            for (Document doc : resultados) {
                // Extraemos los campos del documento BSON
                String nombre = doc.getString("jugador");
                
                // Usamos Object para evitar errores de tipo si el premio es Integer o Long
                Object premio = doc.get("premio");
                
                // Extraemos la fecha y la convertimos a un String legible
                Object fechaObj = doc.get("fecha");
                String fechaStr = (fechaObj != null) ? fechaObj.toString() : "---";

                // Añadimos una nueva fila al modelo de la tabla con la información mapeada
                modelo.addRow(new Object[] {
                    posicion + "º", 
                    nombre, 
                    premio + " €", 
                    fechaStr
                });
                posicion++;
            }
            
            // Si el contador sigue en 1, significa que no se encontró ninguna partida
            if (posicion == 1) {
                JOptionPane.showMessageDialog(this, "Aún no hay partidas registradas en el ranking.");
            }

        } catch (Exception e) {
            // Informamos al usuario en caso de fallo en la conexión con la nube
            JOptionPane.showMessageDialog(this, "Error al conectar con el Ranking: " + e.getMessage());
            e.printStackTrace();
        }
    }
}