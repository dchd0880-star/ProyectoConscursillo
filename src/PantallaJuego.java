import java.util.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.Cursor;

/**
 * Ventana principal donde se desarrolla la partida del concurso.
 * Gestiona la interfaz gráfica, la interacción del jugador y la lógica visual de los comodines.
 */
public class PantallaJuego extends JFrame {

    // Identificador de versión para la serialización de la clase
    private static final long serialVersionUID = 1L;
    
    // Contenedor principal de la ventana
    private JPanel contentPane;
    
    // --- VARIABLES DE LÓGICA Y DATOS ---
    private LogicaJuego logica;               // Instancia que controla las reglas de dinero y aciertos
    private String nombreJugador;             // Nombre introducido por el usuario en el menú
    private ArrayList<Pregunta> listaPreguntas; // Pool de 16 preguntas cargadas desde MongoDB Atlas
    private int indiceActual = 0;             // Rastrea en qué pregunta de la lista estamos actualmente

    // --- COMPONENTES DE LA INTERFAZ ---
    private JLabel lblEnunciado;              // Muestra el texto de la pregunta
    private JLabel lblDinero;                 // Muestra el premio actual acumulado
    private JLabel lblNombreJugador;          // Muestra el nombre del jugador en la esquina
    private JButton btnRespuestaA, btnRespuestaB, btnRespuestaC, btnRespuestaD; // Botones de las 4 opciones
    private JButton btnPlantarse;             // Botón para retirarse con el dinero
    private JLabel lblNewLabel;               // Imagen de fondo/decorativa
    private JLabel lblNewLabel_1;             // Título "COMODINES"
    private JLabel lblNewLabel_2;             // Imagen decorativa de IlloJuan
    
    // Botones de los 5 comodines
    private JButton btnComodin50;
    private JButton btnPublico;
    private JButton btnLlamada;
    private JButton btnRuleta;
    private JButton btnMago;

    /**
     * Constructor: Inicializa la interfaz y carga los datos de la partida.
     * @param logicaRecibida Instancia de LogicaJuego iniciada en el menú.
     * @param nombreRecibido Nombre del jugador.
     */
    public PantallaJuego(LogicaJuego logicaRecibida, String nombreRecibido) {
        setTitle("El Concursillo");
        this.logica = logicaRecibida;
        this.nombreJugador = nombreRecibido;

        // --- CONFIGURACIÓN BÁSICA DE LA VENTANA ---
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 924, 692);
        setResizable(false);
        setLocationRelativeTo(null); // Centra la ventana exactamente en el medio de la pantalla

        // Panel principal con fondo azul oscuro y diseño absoluto (null layout)
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 128)); 
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // =====================================================================
        // 1. CONFIGURACIÓN DE BOTONES DE RESPUESTA (A, B, C, D)
        // =====================================================================
        
        btnRespuestaA = new JButton("A");
        btnRespuestaA.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnRespuestaA.setBorder(null);
        btnRespuestaA.setForeground(Color.WHITE);
        btnRespuestaA.setBackground(new Color(64, 0, 128));
        btnRespuestaA.setBounds(459, 561, 216, 84);
        btnRespuestaA.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de "manita" al pasar por encima
        btnRespuestaA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(0); } // Envía índice 0 para la A
        });
        contentPane.add(btnRespuestaA);

        btnRespuestaB = new JButton("B");
        btnRespuestaB.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnRespuestaB.setBorder(null);
        btnRespuestaB.setForeground(Color.WHITE);
        btnRespuestaB.setBackground(new Color(64, 0, 128));
        btnRespuestaB.setBounds(459, 467, 216, 84);
        btnRespuestaB.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRespuestaB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(1); } // Envía índice 1 para la B
        });
        contentPane.add(btnRespuestaB);

        btnRespuestaC = new JButton("C");
        btnRespuestaC.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnRespuestaC.setBorder(null);
        btnRespuestaC.setForeground(Color.WHITE);
        btnRespuestaC.setBackground(new Color(64, 0, 128));
        btnRespuestaC.setBounds(685, 467, 215, 84);
        btnRespuestaC.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRespuestaC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(2); } // Envía índice 2 para la C
        });
        contentPane.add(btnRespuestaC);

        btnRespuestaD = new JButton("D");
        btnRespuestaD.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnRespuestaD.setBorder(null);
        btnRespuestaD.setForeground(Color.WHITE);
        btnRespuestaD.setBackground(new Color(64, 0, 128));
        btnRespuestaD.setBounds(685, 561, 215, 84);
        btnRespuestaD.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRespuestaD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(3); } // Envía índice 3 para la D
        });
        contentPane.add(btnRespuestaD);

        // =====================================================================
        // 2. ETIQUETAS DE INFORMACIÓN (PREGUNTA, DINERO, NOMBRE)
        // =====================================================================

        // Etiqueta invisible que contendrá el texto de la pregunta
        lblEnunciado = new JLabel("");
        lblEnunciado.setHorizontalAlignment(SwingConstants.CENTER);
        lblEnunciado.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEnunciado.setForeground(Color.WHITE);
        lblEnunciado.setBounds(15, 480, 360, 120);
        contentPane.add(lblEnunciado);

        // Indicador de premio acumulado
        lblDinero = new JLabel("");
        lblDinero.setHorizontalAlignment(SwingConstants.CENTER);
        lblDinero.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDinero.setForeground(Color.WHITE);
        lblDinero.setBounds(749, 11, 149, 47);
        contentPane.add(lblDinero);

        // Muestra el nombre del jugador en la esquina superior izquierda
        lblNombreJugador = new JLabel("JUGADOR: " + nombreJugador);
        lblNombreJugador.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombreJugador.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNombreJugador.setForeground(Color.WHITE);
        lblNombreJugador.setBounds(10, 11, 291, 47);
        contentPane.add(lblNombreJugador);
        
        // --- BOTÓN RENDIRSE ---
        // Permite al jugador abandonar la partida y llevarse el dinero actual
        btnPlantarse = new JButton("RENDIRSE");
        btnPlantarse.setBorder(null);
        btnPlantarse.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnPlantarse.setForeground(new Color(255, 255, 255));
        btnPlantarse.setBackground(new Color(0, 0, 128));
        btnPlantarse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPlantarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dineroGanado = logica.getDineroActual();
                
                // Guarda la partida en la base de datos de MongoDB Atlas antes de cerrar
                ConexionMongo mongo = new ConexionMongo();
                mongo.guardarPartida(nombreJugador, dineroGanado);
                
                // Muestra un mensaje de despedida con el premio
                JOptionPane.showMessageDialog(null, 
                    "¡Sabia decisión, " + nombreJugador + "!\n" +
                    "Has decidido plantarte y te llevas: " + dineroGanado + "€");
          
                dispose(); // Cierra la ventana del juego
            }
        });
        btnPlantarse.setBounds(294, 11, 330, 47);
        contentPane.add(btnPlantarse);
        
        // Imagen decorativa del overlay de las preguntas
        lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(PantallaJuego.class.getResource("/imagenes/FotoPreguntas.png")));
        lblNewLabel.setBounds(189, 11, 709, 500);
        contentPane.add(lblNewLabel);
        
        // =====================================================================
        // 3. SECCIÓN DE COMODINES
        // =====================================================================

        lblNewLabel_1 = new JLabel("COMODINES");
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_1.setBounds(10, 106, 135, 50);
        contentPane.add(lblNewLabel_1);

        // --- COMODÍN 50% ---
        // Elimina el texto de dos botones de respuesta incorrectos
        btnComodin50 = new JButton(" 50%");
        btnComodin50.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnComodin50.setBorder(null);
        btnComodin50.setForeground(new Color(255, 255, 255));
        btnComodin50.setBackground(new Color(64, 0, 128));
        btnComodin50.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnComodin50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pregunta pActual = listaPreguntas.get(indiceActual);
                int correcta = pActual.getRespuestaCorrecta();
                int borradores = 0;

                // Recorre las 4 opciones y borra el texto de las dos primeras incorrectas que encuentre
                for (int i = 0; i < 4; i++) {
                    if (i != correcta && borradores < 2) {
                        if (i == 0) btnRespuestaA.setText("");
                        if (i == 1) btnRespuestaB.setText("");
                        if (i == 2) btnRespuestaC.setText("");
                        if (i == 3) btnRespuestaD.setText("");
                        borradores++;
                    }
                }
                btnComodin50.setEnabled(false); // Deshabilita el botón tras su uso
                btnComodin50.setBackground(Color.GRAY); // Lo pone gris para indicar que está gastado
            }
        });
        btnComodin50.setBounds(10, 148, 135, 37);
        contentPane.add(btnComodin50);
        
        // --- COMODÍN PÚBLICO ---
        // Muestra una estadística simulada que apunta fuertemente a la respuesta correcta
        btnPublico = new JButton("PÚBLICO");
        btnPublico.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnPublico.setBorder(null);
        btnPublico.setForeground(new Color(255, 255, 255));
        btnPublico.setBackground(new Color(64, 0, 128));
        btnPublico.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPublico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pregunta p = listaPreguntas.get(indiceActual);
                // Transforma el índice (0-3) en una letra (A-D)
                char respuestaCorrecta = (char) ('A' + p.getRespuestaCorrecta());

                // Muestra un JOptionPane con estadísticas ficticias
                String estadistica = "Resultados del Público:\n" +
                                     "A: 10%\n" +
                                     "B: 15%\n" +
                                     "C: 65%\n" + 
                                     "D: 10%\n\n" +
                                     "El público cree que la respuesta es la " + respuestaCorrecta;

                JOptionPane.showMessageDialog(null, estadistica, "Comodín del Público", JOptionPane.INFORMATION_MESSAGE);
                
                btnPublico.setEnabled(false);
                btnPublico.setBackground(Color.GRAY);
            }
        });
        btnPublico.setBounds(10, 196, 135, 37);
        contentPane.add(btnPublico);
        
        // --- COMODÍN LLAMADA ---
        // Sugiere la respuesta correcta mediante un cuadro de diálogo de un amigo ficticio
        btnLlamada = new JButton("LLAMADA");
        btnLlamada.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnLlamada.setBorder(null);
        btnLlamada.setForeground(new Color(255, 255, 255));
        btnLlamada.setBackground(new Color(64, 0, 128));
        btnLlamada.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLlamada.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pregunta p = listaPreguntas.get(indiceActual);
                String[] opciones = {"A", "B", "C", "D"};
                String sugerencia = opciones[p.getRespuestaCorrecta()];

                String mensajeLlamada = "Tu amigo dice:\n" +
                                        "'¡Hola! Pues estoy casi seguro de que\n" +
                                        "la respuesta correcta es la " + sugerencia + ".'";

                JOptionPane.showMessageDialog(null, mensajeLlamada, "Comodín de la Llamada", JOptionPane.WARNING_MESSAGE);
                
                btnLlamada.setEnabled(false); 
                btnLlamada.setBackground(Color.GRAY);
            }
        });
        btnLlamada.setBounds(10, 244, 135, 37);
        contentPane.add(btnLlamada);
        
        // --- COMODÍN RULETA ---
        // Genera un número aleatorio para reactivar los comodines básicos (50, Público, Llamada) si se han gastado
        btnRuleta = new JButton("RULETA");
        btnRuleta.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnRuleta.setBorder(null);
        btnRuleta.setForeground(new Color(255, 255, 255));
        btnRuleta.setBackground(new Color(95, 41, 160));
        btnRuleta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRuleta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int recuperados = (int) (Math.random() * 4); // Aleatorio entre 0 y 3
                int contador = 0;

                // Intenta reactivar botones deshabilitados secuencialmente hasta agotar la tirada
                if (!btnComodin50.isEnabled() && contador < recuperados) {
                    btnComodin50.setEnabled(true);
                    btnComodin50.setBackground(new Color(64, 0, 128)); // Vuelve al color original
                    contador++;
                }
                if (!btnPublico.isEnabled() && contador < recuperados) {
                    btnPublico.setEnabled(true);
                    btnPublico.setBackground(new Color(64, 0, 128));
                    contador++;
                }
                if (!btnLlamada.isEnabled() && contador < recuperados) {
                    btnLlamada.setEnabled(true);
                    btnLlamada.setBackground(new Color(64, 0, 128));
                    contador++;
                }

                // Feedback al usuario
                if (recuperados == 0) {
                    JOptionPane.showMessageDialog(null, "¡Pfff, mala suerte illo! La ruleta ha caído en 0.");
                } else {
                    JOptionPane.showMessageDialog(null, "¡La ruleta ha girado y recuperas " + contador + " comodín/es!");
                }

                btnRuleta.setEnabled(false); // Solo se puede girar una vez por partida
                btnRuleta.setBackground(Color.GRAY);
            }
        });
        btnRuleta.setBounds(10, 292, 135, 37);
        contentPane.add(btnRuleta);

        // --- COMODÍN MAGO ---
        // Cambia la pregunta actual por la de reserva (la número 16 del pool). Requiere nivel de seguridad.
        btnMago = new JButton("MAGO");
        btnMago.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnMago.setBorder(null);
        btnMago.setForeground(new Color(255, 255, 255));
        btnMago.setBackground(new Color(95, 41, 160));
        btnMago.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (indiceActual < 4) { // Bloqueo: Solo disponible a partir de la pregunta 5
                    JOptionPane.showMessageDialog(null, "¡El Mago solo aparece a partir de la pregunta 5!", "Mago no disponible", JOptionPane.WARNING_MESSAGE);
                    return; // Aborta la acción para no gastar el comodín
                }

                // Verifica que haya una pregunta de reserva (como cargamos 16, la posición 15 existe)
                if (listaPreguntas.size() > 15) { 
                    // Sustituye la pregunta actual por la última del ArrayList
                    listaPreguntas.set(indiceActual, listaPreguntas.get(15));
                    
                    actualizarTablero(); // Redibuja la interfaz con la nueva pregunta
                    
                    JOptionPane.showMessageDialog(null, "¡El Mago ha cambiado la pregunta!");
                    btnMago.setEnabled(false);
                    btnMago.setBackground(Color.GRAY);
                }
            }
        });
        btnMago.setBounds(10, 340, 135, 37);
        contentPane.add(btnMago);
        
        // Imagen decorativa del personaje (IlloJuan)
        lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(PantallaJuego.class.getResource("/imagenes/IlloJuanPreguntas.png")));
        lblNewLabel_2.setBounds(619, 129, 302, 463);
        contentPane.add(lblNewLabel_2);

        // =====================================================================
        // 4. INICIO DEL MOTOR DEL JUEGO
        // =====================================================================
        
        // Instancia la base de datos y trae las 16 preguntas desde MongoDB Atlas
        BaseDatosLocal bd = new BaseDatosLocal();
        this.listaPreguntas = bd.cargarPreguntas();
        
        // Dibuja la primera pregunta en la pantalla
        actualizarTablero(); 
    }

    /**
     * Extrae la pregunta actual del ArrayList y refresca todos los textos de la interfaz.
     * También controla si el jugador ha llegado al final (victoria de 1 millón).
     */
    public void actualizarTablero() {
        if (indiceActual < 15) { // Mientras no hayamos superado las 15 rondas reglamentarias
            Pregunta p = listaPreguntas.get(indiceActual);
            
            // Renderizado avanzado del enunciado utilizando etiquetas HTML.
            // Permite forzar el salto de línea al llegar a los 280px para que no pise los botones,
            // aplicando además el centrado perfecto y fijando un tamaño de letra adecuado.
            lblEnunciado.setText("<html><body style='width: 280px; text-align: center; font-size: 18px;'>" + p.getEnunciado() + "</body></html>");
            
            // Vuelca las 4 posibles respuestas en los botones
            btnRespuestaA.setText("A: " + p.getOpciones()[0]);
            btnRespuestaB.setText("B: " + p.getOpciones()[1]);
            btnRespuestaC.setText("C: " + p.getOpciones()[2]);
            btnRespuestaD.setText("D: " + p.getOpciones()[3]);
            
            // Muestra cuánto dinero asegura esta ronda
            lblDinero.setText("Dinero: " + logica.getDineroActual() + "€");
        } else {
            // --- CASO DE VICTORIA TOTAL ---
            // Si el índice ha llegado a 15, el jugador ha respondido todo correctamente
            ConexionMongo mongo = new ConexionMongo();
            mongo.guardarPartida(nombreJugador, 1000000); // Guarda el millón en el ranking mundial

            JOptionPane.showMessageDialog(this, "¡ENHORABUENA! ¡ERES MILLONARIO!");
            dispose(); // Cierra el juego
        }
    }

    /**
     * Procesa la pulsación de los botones de respuesta y delega en LogicaJuego para ver si es correcto.
     * @param opcion El índice (0-3) correspondiente a la opción elegida por el jugador.
     */
    private void comprobarRespuesta(int opcion) {
        // Llama a la lógica central. Devuelve true si acierta.
        if (logica.comprobarRespuesta(listaPreguntas.get(indiceActual), opcion)) {
            JOptionPane.showMessageDialog(this, "¡Correcto!");
            indiceActual++;      // Avanza al siguiente nivel
            actualizarTablero(); // Redibuja la interfaz con el siguiente reto
        } else {
            // --- CASO DE FALLO ---
            // Pide a la lógica que determine a qué punto de seguridad cae el jugador
            int premioConsolacion = logica.getDineroSiFalla();
            
            // Guarda la puntuación de consolación en la nube
            ConexionMongo mongo = new ConexionMongo();
            mongo.guardarPartida(nombreJugador, premioConsolacion);

            // Avisa de la derrota y el premio final
            JOptionPane.showMessageDialog(this, "¡Has fallado! Te llevas: " + premioConsolacion + "€");
            dispose(); // Termina la partida
        }
    }
}