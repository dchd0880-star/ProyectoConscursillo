import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.bson.Document;
import com.mongodb.client.*;
import com.mongodb.client.model.Sorts;

public class PantallaRanking extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tablaRanking;
    private DefaultTableModel modeloTabla;
    
    // URI de tu base de datos en Mongo Atlas
    private final String URI = "mongodb+srv://dchd0880_db_user:BFyNiEnlQMgVeVtR@proyectoconcursillo.2z703nl.mongodb.net/";

    public PantallaRanking(JFrame menuPadre) {
        setTitle("El Concursillo - Salón de la Fama");
        setIconImage(Toolkit.getDefaultToolkit().getImage(PantallaRanking.class.getResource("/imagenes/IconoPrincipal.png")));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Lo controlamos con el botón Volver
        setBounds(100, 100, 924, 692);
        setResizable(false);
        setLocationRelativeTo(menuPadre);

        // ====================================================
        // FONDO GENERAL: DEGRADADO RADIAL NOCTURNO
        // ====================================================
        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color colorCentro = new Color(16, 20, 43); 
                Color colorBorde = new Color(5, 6, 12);
                
                RadialGradientPaint rgp = new RadialGradientPaint(
                    new Point(getWidth() / 2, getHeight() / 2), 
                    (float) getWidth() / 1.5f, 
                    new float[]{0.0f, 1.0f}, 
                    new Color[]{colorCentro, colorBorde}
                );
                
                g2.setPaint(rgp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Título superior
        JLabel lblTitulo = new JLabel("SALÓN DE LA FAMA - TOP 10", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(255, 204, 0)); // Dorado premium
        lblTitulo.setBounds(10, 30, 900, 40);
        contentPane.add(lblTitulo);

        // ====================================================
        // CONFIGURACIÓN DE LA TABLA (Estilo Neón / TV)
        // ====================================================
        String[] columnas = {"Posición", "Jugador", "Premio Ganado", "Fecha"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Celdas no editables
        };

        tablaRanking = new JTable(modeloTabla);
        tablaRanking.setFont(new Font("Tahoma", Font.BOLD, 15));
        tablaRanking.setForeground(Color.WHITE);
        tablaRanking.setBackground(new Color(15, 15, 25));
        tablaRanking.setRowHeight(35);
        tablaRanking.setShowGrid(false);
        tablaRanking.setIntercellSpacing(new Dimension(0, 0));
        tablaRanking.setSelectionBackground(new Color(30, 140, 255, 100)); // Resalte azul al seleccionar fila
        tablaRanking.setSelectionForeground(Color.WHITE);
        
        // Renderizado personalizado para centrar texto y colorear el Top 3
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                
                if (!isSelected) {
                    if (row == 0) c.setForeground(new Color(255, 215, 0));      // 1º Oro
                    else if (row == 1) c.setForeground(new Color(192, 192, 192)); // 2º Plata
                    else if (row == 2) c.setForeground(new Color(205, 127, 50));  // 3º Bronce
                    else c.setForeground(Color.WHITE);
                }
                return c;
            }
        };
        
        // Aplicamos el renderizador a todas las columnas
        for (int i = 0; i < tablaRanking.getColumnCount(); i++) {
            tablaRanking.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Diseño de la Cabecera de la tabla
        JTableHeader cabecera = tablaRanking.getTableHeader();
        cabecera.setBackground(new Color(10, 12, 20));
        cabecera.setForeground(new Color(0, 150, 255)); // Azul neón
        cabecera.setFont(new Font("Tahoma", Font.BOLD, 16));
        cabecera.setPreferredSize(new Dimension(cabecera.getWidth(), 40));
        cabecera.setBorder(new LineBorder(new Color(0, 150, 255), 1));
        ((DefaultTableCellRenderer)cabecera.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // ScrollPane contenedor con borde azul
        JScrollPane scrollPane = new JScrollPane(tablaRanking);
        scrollPane.setBounds(80, 100, 760, 415);
        scrollPane.getViewport().setBackground(new Color(15, 15, 25)); // Fondo interno del scroll
        scrollPane.setBorder(new LineBorder(new Color(0, 150, 255), 2, true));
        contentPane.add(scrollPane);

        // ====================================================
        // BOTÓN VOLVER AL MENÚ
        // ====================================================
        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(25, 30, 45));
        btnVolver.setBorder(new LineBorder(new Color(0, 150, 255), 2, true));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setBounds(362, 550, 200, 50);
        
        // Efecto Hover sencillo
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btnVolver.setBackground(new Color(40, 50, 70)); }
            public void mouseExited(java.awt.event.MouseEvent e) { btnVolver.setBackground(new Color(25, 30, 45)); }
        });
        
        btnVolver.addActionListener(e -> {
            menuPadre.setVisible(true); // Reaparece el menú principal
            dispose();                  // Destruye la ventana del ranking
        });
        contentPane.add(btnVolver);

        // Cargamos los datos de internet al abrir la ventana
        cargarRankingDesdeMongo();
    }

    /** Conecta a MongoDB, descarga los mejores 10 registros ordenados por premio y rellena la tabla */
    private void cargarRankingDesdeMongo() {
        NumberFormat formatoDinero = NumberFormat.getInstance(new Locale("es", "ES"));
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        // Usamos un hilo para que la animación fluida de la ventana no se trabe al cargar
        SwingWorker<List<Object[]>, Void> worker = new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                List<Object[]> filas = new ArrayList<>();
                
                try (MongoClient mongoClient = MongoClients.create(URI)) {
                    MongoDatabase database = mongoClient.getDatabase("ProyectoConcursillo");
                    MongoCollection<Document> coleccion = database.getCollection("ranking");

                    // Búsqueda en Atlas: Ordenamos descendentemente por premio (-1) y limitamos a los 10 primeros
                    FindIterable<Document> topJugadores = coleccion.find()
                                                                   .sort(Sorts.descending("premio"))
                                                                   .limit(10);

                    int posicion = 1;
                    for (Document doc : topJugadores) {
                        String nombre = doc.getString("jugador");
                        int premio = doc.getInteger("premio") != null ? doc.getInteger("premio") : 0;
                        Date fecha = doc.getDate("fecha");

                        String premioFormat = formatoDinero.format(premio) + " €";
                        String fechaFormat = fecha != null ? formatoFecha.format(fecha) : "N/D";

                        filas.add(new Object[]{posicion + "º", nombre.toUpperCase(), premioFormat, fechaFormat});
                        posicion++;
                    }
                }
                return filas;
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> resultados = get();
                    for (Object[] fila : resultados) {
                        modeloTabla.addRow(fila);
                    }
                    // Si el ranking está completamente vacío en la BD
                    if (resultados.isEmpty()) {
                        modeloTabla.addRow(new Object[]{"-", "Sin registros todavía", "-", "-"});
                    }
                } catch (Exception ex) {
                    modeloTabla.addRow(new Object[]{"ERROR", "No se pudo cargar el ranking", "-", "-"});
                    System.err.println("Error descargando ranking: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }
}