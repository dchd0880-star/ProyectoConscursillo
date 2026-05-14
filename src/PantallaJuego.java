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

public class PantallaJuego extends JFrame {

	
	//Prueba
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
    private JLabel lblNewLabel_2;

    public PantallaJuego(LogicaJuego logicaRecibida, String nombreRecibido) {
        setTitle("El Concursillo");
        this.logica = logicaRecibida;
        this.nombreJugador = nombreRecibido;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 924, 692);
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 128)); 
        contentPane.setLayout(null);
        setContentPane(contentPane);

        btnRespuestaA = new JButton("A");
        btnRespuestaA.setForeground(Color.WHITE);
        btnRespuestaA.setBackground(new Color(0, 64, 128));
        btnRespuestaA.setBounds(567, 559, 149, 65);
        btnRespuestaA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(0); }
        });
        contentPane.add(btnRespuestaA);

        btnRespuestaB = new JButton("B");
        btnRespuestaB.setForeground(Color.WHITE);
        btnRespuestaB.setBackground(new Color(0, 64, 128));
        btnRespuestaB.setBounds(567, 488, 149, 65);
        btnRespuestaB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(1); }
        });
        contentPane.add(btnRespuestaB);

        btnRespuestaC = new JButton("C");
        btnRespuestaC.setForeground(Color.WHITE);
        btnRespuestaC.setBackground(new Color(0, 64, 128));
        btnRespuestaC.setBounds(726, 488, 135, 65);
        btnRespuestaC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(2); }
        });
        contentPane.add(btnRespuestaC);

        btnRespuestaD = new JButton("D");
        btnRespuestaD.setForeground(Color.WHITE);
        btnRespuestaD.setBackground(new Color(0, 64, 128));
        btnRespuestaD.setBounds(726, 559, 135, 65);
        btnRespuestaD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { comprobarRespuesta(3); }
        });
        contentPane.add(btnRespuestaD);

        lblEnunciado = new JLabel("");
        lblEnunciado.setHorizontalAlignment(SwingConstants.CENTER);
        lblEnunciado.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEnunciado.setForeground(Color.WHITE);
        lblEnunciado.setBounds(10, 497, 545, 97);
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
        btnPlantarse.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnPlantarse.setForeground(Color.WHITE);
        btnPlantarse.setBackground(new Color(128, 128, 128));
        btnPlantarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dineroGanado = logica.getDineroActual();
                
                // <--- AQUÍ GUARDAMOS EN MONGO AL PLANTARSE
                ConexionMongo mongo = new ConexionMongo();
                mongo.guardarPartida(nombreJugador, dineroGanado);
                
                JOptionPane.showMessageDialog(null, 
                    "¡Sabia decisión, " + nombreJugador + "!\n" +
                    "Has decidido plantarte y te llevas: " + dineroGanado + "€");
          
                dispose(); 
            }
        });
        btnPlantarse.setBounds(195, 11, 535, 47);
        contentPane.add(btnPlantarse);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(PantallaJuego.class.getResource("/imagenes/FotoPreguntas.png")));
        lblNewLabel.setBounds(189, 11, 709, 500);
        contentPane.add(lblNewLabel);
        
        btnComodin50 = new JButton("Comodin 50%");
        btnComodin50.setForeground(new Color(255, 255, 255));
        btnComodin50.setBackground(new Color(128, 128, 128));
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
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        lblNewLabel_1.setBounds(10, 106, 135, 50);
        contentPane.add(lblNewLabel_1);
        
        btnPublico = new JButton("Comodin Publico");
        btnPublico.setForeground(new Color(255, 255, 255));
        btnPublico.setBackground(new Color(128, 128, 128));
        btnPublico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pregunta p = listaPreguntas.get(indiceActual);
                char respuestaCorrecta = ' ';
                if(p.getRespuestaCorrecta() == 0) respuestaCorrecta = 'A';
                if(p.getRespuestaCorrecta() == 1) respuestaCorrecta = 'B';
                if(p.getRespuestaCorrecta() == 2) respuestaCorrecta = 'C';
                if(p.getRespuestaCorrecta() == 3) respuestaCorrecta = 'D';

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
        
        btnLlamada = new JButton("Comodin LLamada");
        btnLlamada.setForeground(new Color(255, 255, 255));
        btnLlamada.setBackground(new Color(128, 128, 128));
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
        
        lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(PantallaJuego.class.getResource("/imagenes/IlloJuanPreguntas.png")));
        lblNewLabel_2.setBounds(619, 129, 302, 463);
        contentPane.add(lblNewLabel_2);

        BaseDatosLocal bd = new BaseDatosLocal();
        this.listaPreguntas = bd.cargarPreguntas();
        actualizarTablero();
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
            // <--- AQUÍ GUARDAMOS EN MONGO AL GANAR EL MILLÓN
            ConexionMongo mongo = new ConexionMongo();
            mongo.guardarPartida(nombreJugador, 1000000);

            JOptionPane.showMessageDialog(this, "¡ENHORABUENA! ¡ERES MILLONARIO!");
            dispose();
        }
    }

    private void comprobarRespuesta(int opcion) {
        if (logica.comprobarRespuesta(listaPreguntas.get(indiceActual), opcion)) {
            JOptionPane.showMessageDialog(this, "¡Correcto!");
            indiceActual++;
            actualizarTablero();
        } else {
            int premioConsolacion = logica.getDineroSiFalla();
            
            ConexionMongo mongo = new ConexionMongo();
            mongo.guardarPartida(nombreJugador, premioConsolacion);

            JOptionPane.showMessageDialog(this, "¡Illooo fallaste! Te llevas: " + premioConsolacion + "€");
            dispose(); 
        }
    }
}