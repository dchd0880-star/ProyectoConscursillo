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

// Ventana principal donde se desarrolla la partida del concurso
public class PantallaJuego extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    // Referencias a la lógica, datos del jugador y pool de preguntas
    private LogicaJuego logica;
    private String nombreJugador;
    private ArrayList<Pregunta> listaPreguntas;
    private int indiceActual = 0; // Rastrea en qué pregunta de la lista estamos

    // Componentes de la interfaz (Etiquetas y Botones)
    private JLabel lblEnunciado;
    private JLabel lblDinero;
    private JLabel lblNombreJugador;
    private JButton btnRespuestaA, btnRespuestaB, btnRespuestaC, btnRespuestaD, btnPlantarse;
    private JLabel lblNewLabel;
    private JButton btnComodin50;
    private JLabel lblNewLabel_1;
    private JButton btnPublico;
    private JButton btnLlamada;
    private JButton btnRuleta;
    private JButton btnMago;
    private JLabel lblNewLabel_2;

    // Constructor: Inicializa la interfaz y carga los datos de la partida
    public PantallaJuego(LogicaJuego logicaRecibida, String nombreRecibido) {
        setTitle("El Concursillo");
        this.logica = logicaRecibida;
        this.nombreJugador = nombreRecibido;

        // Configuración de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 924, 692);
        setResizable(false);
        setLocationRelativeTo(null); // Centra la ventana en pantalla

        // Panel principal con fondo azul oscuro y diseño absoluto (null layout)
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 128)); 
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // --- CONFIGURACIÓN DE BOTONES DE RESPUESTA (A, B, C, D) ---
        
        btnRespuestaA = new JButton("A");
        btnRespuestaA.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnRespuestaA.setBorder(null);
        btnRespuestaA.setForeground(Color.WHITE);
        btnRespuestaA.setBackground(new Color(64, 0, 128));
        btnRespuestaA.setBounds(459, 561, 216, 84);
        btnRespuestaA.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRespuestaA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(0); }
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
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(1); }
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
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(2); }
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
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(3); }
        });
        contentPane.add(btnRespuestaD);

        // --- ETIQUETAS DE INFORMACIÓN (PREGUNTA, DINERO, NOMBRE) ---

        lblEnunciado = new JLabel("");
        lblEnunciado.setHorizontalAlignment(SwingConstants.CENTER);
        lblEnunciado.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEnunciado.setForeground(Color.WHITE);
        lblEnunciado.setBounds(15, 480, 360, 120);
        contentPane.add(lblEnunciado);

        lblDinero = new JLabel("");
        lblDinero.setHorizontalAlignment(SwingConstants.CENTER);
        lblDinero.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDinero.setForeground(Color.WHITE);
        lblDinero.setBounds(749, 11, 149, 47);
        contentPane.add(lblDinero);

        lblNombreJugador = new JLabel("JUGADOR: " + nombreJugador);
        lblNombreJugador.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombreJugador.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNombreJugador.setForeground(Color.WHITE);
        lblNombreJugador.setBounds(10, 11, 291, 47);
        contentPane.add(lblNombreJugador);
        
        // Botón para retirarse con el premio acumulado actual
        btnPlantarse = new JButton("RENDIRSE");
        btnPlantarse.setBorder(null);
        btnPlantarse.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnPlantarse.setForeground(new Color(255, 255, 255));
        btnPlantarse.setBackground(new Color(0, 0, 128));
        btnPlantarse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPlantarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dineroGanado = logica.getDineroActual();
                
                // Guarda la partida en MongoDB Atlas antes de cerrar
                ConexionMongo mongo = new ConexionMongo();
                mongo.guardarPartida(nombreJugador, dineroGanado);
                
                JOptionPane.showMessageDialog(null, 
                    "¡Sabia decisión, " + nombreJugador + "!\n" +
                    "Has decidido plantarte y te llevas: " + dineroGanado + "€");
          
                dispose(); 
            }
        });
        btnPlantarse.setBounds(294, 11, 330, 47);
        contentPane.add(btnPlantarse);
        
        // Imágenes decorativas y logotipos
        lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(PantallaJuego.class.getResource("/imagenes/FotoPreguntas.png")));
        lblNewLabel.setBounds(189, 11, 709, 500);
        contentPane.add(lblNewLabel);
        
        // --- SECCIÓN DE COMODINES ---

        lblNewLabel_1 = new JLabel("COMODINES");
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_1.setBounds(10, 106, 135, 50);
        contentPane.add(lblNewLabel_1);

        // Comodín 50%: Elimina dos opciones incorrectas
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

                // Recorre las opciones y borra el texto de las dos primeras incorrectas que encuentre
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
                btnComodin50.setBackground(Color.GRAY);
            }
        });
        btnComodin50.setBounds(10, 148, 135, 37);
        contentPane.add(btnComodin50);
        
        // Comodín Público: Muestra una estadística simulada de acierto
        btnPublico = new JButton("PÚBLICO");
        btnPublico.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnPublico.setBorder(null);
        btnPublico.setForeground(new Color(255, 255, 255));
        btnPublico.setBackground(new Color(64, 0, 128));
        btnPublico.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPublico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pregunta p = listaPreguntas.get(indiceActual);
                char respuestaCorrecta = (char) ('A' + p.getRespuestaCorrecta());

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
        
        // Comodín Llamada: Sugiere la respuesta correcta mediante un diálogo
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
        
        // Comodín Ruleta: Recupera aleatoriamente entre 0 y 3 comodines gastados
        btnRuleta = new JButton("RULETA");
        btnRuleta.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnRuleta.setBorder(null);
        btnRuleta.setForeground(new Color(255, 255, 255));
        btnRuleta.setBackground(new Color(95, 41, 160));
        btnRuleta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRuleta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int recuperados = (int) (Math.random() * 4);
                int contador = 0;

                // Intenta reactivar los comodines básicos si están deshabilitados
                if (!btnComodin50.isEnabled() && contador < recuperados) {
                    btnComodin50.setEnabled(true);
                    btnComodin50.setBackground(new Color(64, 0, 128));
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

                if (recuperados == 0) {
                    JOptionPane.showMessageDialog(null, "¡Pfff, mala suerte illo! La ruleta ha caído en 0.");
                } else {
                    JOptionPane.showMessageDialog(null, "¡La ruleta ha girado y recuperas " + contador + " comodín/es!");
                }

                btnRuleta.setEnabled(false);
                btnRuleta.setBackground(Color.GRAY);
            }
        });
        btnRuleta.setBounds(10, 292, 135, 37);
        contentPane.add(btnRuleta);

        // Comodín Mago: Cambia la pregunta actual por una de reserva (solo nivel 5+)
        btnMago = new JButton("MAGO");
        btnMago.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnMago.setBorder(null);
        btnMago.setForeground(new Color(255, 255, 255));
        btnMago.setBackground(new Color(95, 41, 160));
        btnMago.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (indiceActual < 4) { // Bloqueo por nivel de seguridad
                    JOptionPane.showMessageDialog(null, "¡El Mago solo aparece a partir de la pregunta 5!", "Mago no disponible", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (listaPreguntas.size() > 15) { // Usa la pregunta nº 16 del pool como reserva
                    listaPreguntas.set(indiceActual, listaPreguntas.get(15));
                    actualizarTablero(); 
                    JOptionPane.showMessageDialog(null, "¡El Mago ha cambiado la pregunta!");
                    btnMago.setEnabled(false);
                    btnMago.setBackground(Color.GRAY);
                }
            }
        });
        btnMago.setBounds(10, 340, 135, 37);
        contentPane.add(btnMago);
        
        lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(PantallaJuego.class.getResource("/imagenes/IlloJuanPreguntas.png")));
        lblNewLabel_2.setBounds(619, 129, 302, 463);
        contentPane.add(lblNewLabel_2);

        // Carga de preguntas desde MongoDB Atlas al iniciar la ventana
        BaseDatosLocal bd = new BaseDatosLocal();
        this.listaPreguntas = bd.cargarPreguntas();
        actualizarTablero(); // Dibuja la primera pregunta
    }

    // Refresca los textos de la interfaz con la pregunta actual del pool
    public void actualizarTablero() {
        if (indiceActual < 15) { // Mientras no hayamos completado las 15 preguntas
            Pregunta p = listaPreguntas.get(indiceActual);
            
            // Renderizado de la pregunta con HTML para soportar saltos de línea y centrado
            lblEnunciado.setText("<html><body style='width: 280px; text-align: center; font-size: 18px;'>" + p.getEnunciado() + "</body></html>");
            
            btnRespuestaA.setText("A: " + p.getOpciones()[0]);
            btnRespuestaB.setText("B: " + p.getOpciones()[1]);
            btnRespuestaC.setText("C: " + p.getOpciones()[2]);
            btnRespuestaD.setText("D: " + p.getOpciones()[3]);
            lblDinero.setText("Dinero: " + logica.getDineroActual() + "€");
        } else {
            // Caso de victoria total: guarda el millón y termina
            ConexionMongo mongo = new ConexionMongo();
            mongo.guardarPartida(nombreJugador, 1000000);

            JOptionPane.showMessageDialog(this, "¡ENHORABUENA! ¡ERES MILLONARIO!");
            dispose();
        }
    }

    // Procesa el clic en una de las respuestas A, B, C o D
    private void comprobarRespuesta(int opcion) {
        if (logica.comprobarRespuesta(listaPreguntas.get(indiceActual), opcion)) {
            JOptionPane.showMessageDialog(this, "¡Correcto!");
            indiceActual++;
            actualizarTablero(); // Avanza a la siguiente pregunta
        } else {
            // Caso de fallo: Calcula premio según nivel de seguridad y guarda en Atlas
            int premioConsolacion = logica.getDineroSiFalla();
            
            ConexionMongo mongo = new ConexionMongo();
            mongo.guardarPartida(nombreJugador, premioConsolacion);

            JOptionPane.showMessageDialog(this, "¡Has fallado! Te llevas: " + premioConsolacion + "€");
            dispose(); 
        }
    }
}