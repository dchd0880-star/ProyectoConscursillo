import java.util.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public class PantallaJuego extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private LogicaJuego logica;
    private String nombreJugador;
    private ArrayList<Pregunta> listaPreguntas;
    private int indiceActual = 0;

    private JLabel lblEnunciado;
    private JLabel lblDinero;
    private JLabel lblNombreJugador;
    private JButton btnRespuestaA, btnRespuestaB, btnRespuestaC, btnRespuestaD, btnPlantarse;
    private JLabel lblNewLabel;
    private JButton btnComodin50;
    private JLabel lblNewLabel_1;
    private JButton btnPublico;
    private JButton btnLlamada;

    // ==========================================
    // COMPONENTES DEL PANEL DE MENSAJES INTERNO
    // ==========================================
    private JPanel panelMensaje;
    private JLabel lblTituloMensaje;
    private JTextArea txtMensaje;
    private JButton btnAceptarMensaje;
    // 0: Solo cerrar panel | 1: Siguiente pregunta | 2: Cerrar ventana (Fin de partida)
    private int accionPosterior = 0; 

    public PantallaJuego(LogicaJuego logicaRecibida, String nombreRecibido) {
        setTitle("El Concursillo");
        this.logica = logicaRecibida;
        this.nombreJugador = nombreRecibido;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 924, 692);
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(187, 119, 255)); 
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // ====================================================
        // CONFIGURACIÓN DEL PANEL SUPERPUESTO (Estilo Info)
        // ====================================================
        panelMensaje = new JPanel();
        panelMensaje.setBounds(160, 150, 600, 350);
        panelMensaje.setBackground(new Color(40, 20, 70)); // Morado oscuro elegante
        panelMensaje.setBorder(new LineBorder(Color.WHITE, 3, true));
        panelMensaje.setLayout(null);
        panelMensaje.setVisible(false); // Oculto al inicio
        contentPane.add(panelMensaje);

        lblTituloMensaje = new JLabel("TÍTULO");
        lblTituloMensaje.setForeground(Color.WHITE);
        lblTituloMensaje.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTituloMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloMensaje.setBounds(10, 20, 580, 35);
        panelMensaje.add(lblTituloMensaje);

        txtMensaje = new JTextArea();
        txtMensaje.setFont(new Font("Tahoma", Font.PLAIN, 18));
        txtMensaje.setForeground(Color.WHITE);
        txtMensaje.setBackground(new Color(40, 20, 70));
        txtMensaje.setEditable(false);
        txtMensaje.setFocusable(false);
        txtMensaje.setLineWrap(true);
        txtMensaje.setWrapStyleWord(true);
        txtMensaje.setBounds(40, 80, 520, 180);
        panelMensaje.add(txtMensaje);

        btnAceptarMensaje = new JButton("Aceptar");
        btnAceptarMensaje.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnAceptarMensaje.setBackground(Color.WHITE);
        btnAceptarMensaje.setForeground(new Color(40, 20, 70));
        btnAceptarMensaje.setBounds(230, 280, 140, 40);
        btnAceptarMensaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelMensaje.setVisible(false);
                if (accionPosterior == 1) {
                    indiceActual++;
                    actualizarTablero();
                } else if (accionPosterior == 2) {
                    dispose(); // Cierra la pantalla de juego al terminar
                }
            }
        });
        panelMensaje.add(btnAceptarMensaje);

        // Aseguramos que el panel siempre esté por encima del resto de botones e imágenes
        contentPane.setComponentZOrder(panelMensaje, 0);

        // ==========================================
        // BOTONES DE RESPUESTA Y COMPONENTES
        // ==========================================
        btnRespuestaA = new JButton("A");
        btnRespuestaA.setBounds(75, 443, 309, 97);
        btnRespuestaA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(0); }
        });
        contentPane.add(btnRespuestaA);

        btnRespuestaB = new JButton("B");
        btnRespuestaB.setBounds(75, 551, 309, 97);
        btnRespuestaB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(1); }
        });
        contentPane.add(btnRespuestaB);

        btnRespuestaC = new JButton("C");
        btnRespuestaC.setBounds(495, 443, 309, 97);
        btnRespuestaC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(2); }
        });
        contentPane.add(btnRespuestaC);

        btnRespuestaD = new JButton("D");
        btnRespuestaD.setBounds(495, 551, 309, 97);
        btnRespuestaD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(3); }
        });
        contentPane.add(btnRespuestaD);

        lblEnunciado = new JLabel("");
        lblEnunciado.setHorizontalAlignment(SwingConstants.CENTER);
        lblEnunciado.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEnunciado.setForeground(Color.WHITE);
        lblEnunciado.setBounds(142, 59, 719, 97);
        contentPane.add(lblEnunciado);

        lblDinero = new JLabel("");
        lblDinero.setHorizontalAlignment(SwingConstants.CENTER);
        lblDinero.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDinero.setForeground(Color.WHITE);
        lblDinero.setBounds(749, 11, 149, 47);
        contentPane.add(lblDinero);

        lblNombreJugador = new JLabel("Jugador: " + nombreJugador);
        lblNombreJugador.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombreJugador.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNombreJugador.setForeground(Color.WHITE);
        lblNombreJugador.setBounds(10, 11, 149, 47);
        contentPane.add(lblNombreJugador);
        
        btnPlantarse = new JButton("Rendirse");
        btnPlantarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dineroGanado = logica.getDineroActual();
                
                ConexionMongo mongo = new ConexionMongo();
                mongo.guardarPartida(nombreJugador, dineroGanado);
                
                mostrarMensaje("DECISIÓN TOMADA", 
                    "\n¡Sabia decisión, " + nombreJugador.toUpperCase() + "!\n\n" +
                    "Has decidido plantarte y te llevas: " + dineroGanado + "€", 2);
            }
        });
        btnPlantarse.setBounds(195, 11, 516, 37);
        contentPane.add(btnPlantarse);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(PantallaJuego.class.getResource("/imagenes/FotoPreguntas.png")));
        lblNewLabel.setBounds(189, 11, 709, 500);
        contentPane.add(lblNewLabel);
        
        btnComodin50 = new JButton("Comodin 50%");
        btnComodin50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pregunta pActual = listaPreguntas.get(indiceActual);
                int correcta = pActual.getRespuestaCorrecta();
                int borradores = 0;

                for (int i = 0; i < 4; i++) {
                    if (i != correcta && borradores < 2) {
                        if (i == 0) btnRespuestaA.setText("");
                        if (i == 1) btnRespuestaB.setText("");
                        if (i == 2) btnRespuestaC.setText("");
                        if (i == 3) btnRespuestaD.setText("");
                        borradores++;
                    }
                }
                btnComodin50.setEnabled(false); 
                btnComodin50.setBackground(Color.GRAY);
            }
        });
        btnComodin50.setBounds(10, 148, 135, 37);
        contentPane.add(btnComodin50);
        
        lblNewLabel_1 = new JLabel("COMODINES");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        lblNewLabel_1.setBounds(10, 106, 110, 50);
        contentPane.add(lblNewLabel_1);
        
        btnPublico = new JButton("Comodin Publico");
        btnPublico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pregunta p = listaPreguntas.get(indiceActual);
                char respuestaCorrecta = ' ';
                if(p.getRespuestaCorrecta() == 0) respuestaCorrecta = 'A';
                if(p.getRespuestaCorrecta() == 1) respuestaCorrecta = 'B';
                if(p.getRespuestaCorrecta() == 2) respuestaCorrecta = 'C';
                if(p.getRespuestaCorrecta() == 3) respuestaCorrecta = 'D';

                String estadistica = "Resultados votación del público:\n\n" +
                                     "  • Opción A: 10%\n" +
                                     "  • Opción B: 15%\n" +
                                     "  • Opción C: 65%\n" + 
                                     "  • Opción D: 10%\n\n" +
                                     "El público cree mayoritariamente que la respuesta es la " + respuestaCorrecta;

                mostrarMensaje("COMODÍN DEL PÚBLICO", estadistica, 0);
                
                btnPublico.setEnabled(false);
                btnPublico.setBackground(Color.GRAY);
            }
        });
        btnPublico.setBounds(10, 196, 135, 37);
        contentPane.add(btnPublico);
        
        btnLlamada = new JButton("Comodin LLamada");
        btnLlamada.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pregunta p = listaPreguntas.get(indiceActual);
                String[] opciones = {"A", "B", "C", "D"};
                String sugerencia = opciones[p.getRespuestaCorrecta()];

                String mensajeLlamada = "\nTu amigo al teléfono te dice:\n\n" +
                                        "\"¡Hola! Pues a ver, no te lo garantizo al 100% pero estoy casi seguro de que la respuesta correcta es la " + sugerencia + ".\"";

                mostrarMensaje("COMODÍN DE LA LLAMADA", mensajeLlamada, 0);
                
                btnLlamada.setEnabled(false); 
                btnLlamada.setBackground(Color.GRAY);
            }
        });
        btnLlamada.setBounds(10, 244, 135, 37);
        contentPane.add(btnLlamada);

        BaseDatosLocal bd = new BaseDatosLocal();
        this.listaPreguntas = bd.cargarPreguntas();
        actualizarTablero();
    }

    // Método auxiliar para personalizar y abrir el panel superpuesto fácilmente
    private void mostrarMensaje(String titulo, String texto, int accion) {
        lblTituloMensaje.setText(titulo);
        txtMensaje.setText(texto);
        this.accionPosterior = accion;
        panelMensaje.setVisible(true);
        contentPane.setComponentZOrder(panelMensaje, 0);
        contentPane.repaint();
    }

    public void actualizarTablero() {
        if (indiceActual < listaPreguntas.size()) {
            Pregunta p = listaPreguntas.get(indiceActual);
            lblEnunciado.setText("<html><body style='width: 500px'>" + p.getEnunciado() + "</body></html>");
            btnRespuestaA.setText("A: " + p.getOpciones()[0]);
            btnRespuestaB.setText("B: " + p.getOpciones()[1]);
            btnRespuestaC.setText("C: " + p.getOpciones()[2]);
            btnRespuestaD.setText("D: " + p.getOpciones()[3]);
            lblDinero.setText("Dinero: " + logica.getDineroActual() + "€");
        } else {
            ConexionMongo mongo = new ConexionMongo();
            mongo.guardarPartida(nombreJugador, 1000000);

            mostrarMensaje("¡VICTORIA ABSOLUTA!", 
                "\n¡ENHORABUENA " + nombreJugador.toUpperCase() + "!\n\n" +
                "Has respondido las 15 preguntas correctamente. ¡ERES MILLONARIO!", 2);
        }
    }

    private void comprobarRespuesta(int opcion) {
        if (logica.comprobarRespuesta(listaPreguntas.get(indiceActual), opcion)) {
            // Mostrar panel de acierto y programar el avance a la siguiente pregunta
            mostrarMensaje("¡RESPUESTA CORRECTA!", "\n¡Muy bien jugado!\n\nLa respuesta seleccionada es totalmente correcta.", 1);
        } else {
            int premioConsolacion = logica.getDineroSiFalla();
            
            ConexionMongo mongo = new ConexionMongo();
            mongo.guardarPartida(nombreJugador, premioConsolacion);

            mostrarMensaje("¡RESPUESTA INCORRECTA!", 
                "\n¡Illooo fallaste!\n\nHas marcado la opción equivocada. Te llevas el premio de seguridad acumulado: " + premioConsolacion + "€", 2);
        }
    }
}