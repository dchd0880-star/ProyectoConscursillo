import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.border.LineBorder;

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
    private BotonConcursillo btnRespuestaA, btnRespuestaB, btnRespuestaC, btnRespuestaD, btnPlantarse;
    private JLabel lblLogoFondo;
    private BotonConcursillo btnComodin50, btnPublico, btnLlamada;
    private PanelProgreso panelProgreso;
    
    // Evita que el usuario haga clics dobles mientras se procesa la transición
    private boolean bloqueado = false;

    public enum EstadoRespuesta { NEUTRO, ACIERTO, FALLO }

    public PantallaJuego(LogicaJuego logicaRecibida, String nombreRecibido) {
        setTitle("El Concursillo");
        this.logica = logicaRecibida;
        this.nombreJugador = nombreRecibido;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 924, 692);
        setResizable(false);
        setLocationRelativeTo(null);

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

        // ====================================================
        // MARCADORES SUPERIORES Y BOTÓN RENDIRSE
        // ====================================================
        lblNombreJugador = new JLabel("Jugador: " + nombreJugador);
        lblNombreJugador.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNombreJugador.setForeground(new Color(150, 255, 255)); 
        lblNombreJugador.setBounds(20, 12, 320, 30);
        contentPane.add(lblNombreJugador);

        lblDinero = new JLabel("Dinero: 0 €");
        lblDinero.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblDinero.setForeground(new Color(255, 204, 0)); 
        lblDinero.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDinero.setBounds(684, 12, 200, 30);
        contentPane.add(lblDinero);

        btnPlantarse = new BotonConcursillo("Rendirse", new Color(16, 18, 27), new Color(0, 140, 255), new Color(30, 35, 50));
        btnPlantarse.setBounds(362, 10, 200, 35);
        btnPlantarse.addActionListener(e -> {
            if (bloqueado) return;
            int dineroGanado = logica.getDineroActual();
            ConexionMongo mongo = new ConexionMongo();
            mongo.guardarPartida(nombreJugador, dineroGanado);
            mostrarMensaje("DECISIÓN TOMADA", "\n¡Sabia decisión!\n\nTe plantas y te llevas: " + dineroGanado + " €", 2);
        });
        contentPane.add(btnPlantarse);

        // ====================================================
        // COMODINES
        // ====================================================
        JLabel lblTituloComodines = new JLabel("COMODINES");
        lblTituloComodines.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTituloComodines.setForeground(new Color(150, 255, 255)); 
        lblTituloComodines.setBounds(20, 55, 150, 25);
        contentPane.add(lblTituloComodines);

        btnComodin50 = new BotonConcursillo("50 : 50", new Color(16, 18, 27), new Color(0, 140, 255), new Color(30, 35, 50));
        btnComodin50.setBounds(20, 90, 130, 40);
        btnComodin50.addActionListener(e -> {
            if (bloqueado) return;
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
        });
        contentPane.add(btnComodin50);

        btnPublico = new BotonConcursillo("Público", new Color(16, 18, 27), new Color(0, 140, 255), new Color(30, 35, 50));
        btnPublico.setBounds(20, 140, 130, 40);
        btnPublico.addActionListener(e -> {
            if (bloqueado) return;
            char resp = 'A';
            if (listaPreguntas.get(indiceActual).getRespuestaCorrecta() == 1) resp = 'B';
            if (listaPreguntas.get(indiceActual).getRespuestaCorrecta() == 2) resp = 'C';
            if (listaPreguntas.get(indiceActual).getRespuestaCorrecta() == 3) resp = 'D';
            mostrarMensaje("VOTACIÓN DEL PÚBLICO", "\nLa mayoría del público opina que la respuesta correcta es la opción: " + resp, 0);
            btnPublico.setEnabled(false);
        });
        contentPane.add(btnPublico);

        btnLlamada = new BotonConcursillo("Llamada", new Color(16, 18, 27), new Color(0, 140, 255), new Color(30, 35, 50));
        btnLlamada.setBounds(20, 190, 130, 40);
        btnLlamada.addActionListener(e -> {
            if (bloqueado) return;
            String[] opc = {"A", "B", "C", "D"};
            String sug = opc[listaPreguntas.get(indiceActual).getRespuestaCorrecta()];
            mostrarMensaje("LLAMADA TELEFÓNICA", "\nTu amigo te dice:\n\"Estoy casi seguro de que es la " + sug + ".\"", 0);
            btnLlamada.setEnabled(false);
        });
        contentPane.add(btnLlamada);

        // ====================================================
        // CAMINO DE PREGUNTAS
        // ====================================================
        panelProgreso = new PanelProgreso();
        panelProgreso.setBounds(62, 275, 800, 45);
        contentPane.add(panelProgreso);

        // ====================================================
        // ZONA CENTRAL: PREGUNTA
        // ====================================================
        PanelPreguntaConcursillo panelPregunta = new PanelPreguntaConcursillo();
        panelPregunta.setBounds(62, 330, 800, 60); 
        contentPane.add(panelPregunta);

        lblEnunciado = new JLabel("");
        lblEnunciado.setHorizontalAlignment(SwingConstants.CENTER);
        panelPregunta.add(lblEnunciado, BorderLayout.CENTER);

        // ====================================================
        // BOTONES DE RESPUESTA
        // ====================================================
        Color colorBgRespuesta = new Color(13, 15, 22, 240);
        Color colorBordeRespuesta = new Color(0, 130, 255);
        Color colorHoverRespuesta = new Color(25, 30, 45, 240);

        btnRespuestaA = new BotonConcursillo("", colorBgRespuesta, colorBordeRespuesta, colorHoverRespuesta);
        btnRespuestaA.setBounds(62, 415, 380, 80);
        btnRespuestaA.addActionListener(e -> comprobarRespuesta(0));
        contentPane.add(btnRespuestaA);

        btnRespuestaB = new BotonConcursillo("", colorBgRespuesta, colorBordeRespuesta, colorHoverRespuesta);
        btnRespuestaB.setBounds(482, 415, 380, 80);
        btnRespuestaB.addActionListener(e -> comprobarRespuesta(1));
        contentPane.add(btnRespuestaB);

        btnRespuestaC = new BotonConcursillo("", colorBgRespuesta, colorBordeRespuesta, colorHoverRespuesta);
        btnRespuestaC.setBounds(62, 520, 380, 80);
        btnRespuestaC.addActionListener(e -> comprobarRespuesta(2));
        contentPane.add(btnRespuestaC);

        btnRespuestaD = new BotonConcursillo("", colorBgRespuesta, colorBordeRespuesta, colorHoverRespuesta);
        btnRespuestaD.setBounds(482, 520, 380, 80);
        btnRespuestaD.addActionListener(e -> comprobarRespuesta(3));
        contentPane.add(btnRespuestaD);

        // ====================================================
        // LOGO DE FONDO
        // ====================================================
        lblLogoFondo = new JLabel("");
        lblLogoFondo.setIcon(cargarLogoCentroEscalado());
        lblLogoFondo.setBounds(112, 150, 700, 370); 
        lblLogoFondo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblLogoFondo);
        
        contentPane.setComponentZOrder(lblLogoFondo, contentPane.getComponentCount() - 1);

        BaseDatosLocal bd = new BaseDatosLocal();
        this.listaPreguntas = bd.cargarPreguntas();
        actualizarTablero();
    }

    private ImageIcon cargarLogoCentroEscalado() {
        try {
            java.net.URL url = PantallaJuego.class.getResource("/imagenes/FotoPreguntas.png");
            if (url != null) {
                ImageIcon original = new ImageIcon(url);
                Image img = original.getImage().getScaledInstance(700, 370, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void formatearBotonRespuesta(BotonConcursillo btn, String letra, String textoOpcion) {
        String html = "<html><table width='330' cellpadding='0' cellspacing='0'>" +
                      "<tr>" +
                      "<td width='45' align='left' valign='middle'><b><font color='#FF9600' size='5'>&nbsp;" + letra + ":</font></b></td>" +
                      "<td align='center' valign='middle'><font color='white' size='4'>&nbsp;" + textoOpcion + "&nbsp;</font></td>" +
                      "</tr></table></html>";
        btn.setText(html);
    }

    private void actualizarColoresRespuestas(int elegidaIdx, boolean esCorrecta) {
        BotonConcursillo[] botones = {btnRespuestaA, btnRespuestaB, btnRespuestaC, btnRespuestaD};
        for (BotonConcursillo btn : botones) {
            btn.isHovered = false; 
        }

        if (esCorrecta) {
            botones[elegidaIdx].setEstado(EstadoRespuesta.ACIERTO);
            for (int i = 0; i < 4; i++) {
                if (i != elegidaIdx) {
                    botones[i].setEstado(EstadoRespuesta.FALLO);
                }
            }
        } else {
            for (int i = 0; i < 4; i++) {
                botones[i].setEstado(EstadoRespuesta.FALLO);
            }
        }
        contentPane.repaint();
    }

    // =========================================================================
    // VENTANAS EMERGENTES (Solo para Comodines, Fallos o Victoria final)
    // =========================================================================
    private void mostrarMensaje(String titulo, String texto, int accion) {
        JDialog dialogo = new JDialog(this, true);
        dialogo.setUndecorated(true);
        dialogo.setSize(600, 350);
        dialogo.setLocationRelativeTo(this);
        dialogo.setBackground(new Color(0, 0, 0, 0));
        
        JPanel panelDialogo = new JPanel(null) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(new Color(10, 12, 20)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                
                g2.setColor(new Color(0, 150, 255));
                g2.setStroke(new BasicStroke(3.0f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 40, 40);
                
                g2.dispose();
            }
        };
        panelDialogo.setOpaque(false); 
        
        JLabel lblTit = new JLabel(titulo, SwingConstants.CENTER);
        lblTit.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTit.setForeground(new Color(255, 150, 0));
        lblTit.setBounds(10, 20, 580, 35);
        panelDialogo.add(lblTit);
        
        JTextArea areaTexto = new JTextArea(texto);
        areaTexto.setFont(new Font("Tahoma", Font.PLAIN, 18));
        areaTexto.setForeground(Color.WHITE);
        areaTexto.setOpaque(false); 
        areaTexto.setEditable(false);
        areaTexto.setFocusable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        areaTexto.setBounds(20, 75, 560, 180);
        panelDialogo.add(areaTexto);
        
        BotonConcursillo btnOk = new BotonConcursillo("Aceptar", new Color(25, 30, 45), new Color(0, 150, 255), new Color(40, 50, 70));
        btnOk.setBounds(230, 275, 140, 45);
        btnOk.addActionListener(e -> {
            dialogo.dispose();
            if (accion == 2) {
                dispose();
            }
        });
        panelDialogo.add(btnOk);
        
        dialogo.setContentPane(panelDialogo);
        dialogo.setVisible(true);
    }

    public void actualizarTablero() {
        if (indiceActual < listaPreguntas.size()) {
            Pregunta p = listaPreguntas.get(indiceActual);
            lblEnunciado.setText("<html><div style='text-align: center; width: 760px; font-family: Tahoma; font-size: 15px; color: white;'><b>" + p.getEnunciado() + "</b></div></html>");
            
            BotonConcursillo[] botones = {btnRespuestaA, btnRespuestaB, btnRespuestaC, btnRespuestaD};
            for (BotonConcursillo btn : botones) {
                btn.setEstado(EstadoRespuesta.NEUTRO);
            }

            formatearBotonRespuesta(btnRespuestaA, "A", p.getOpciones()[0]);
            formatearBotonRespuesta(btnRespuestaB, "B", p.getOpciones()[1]);
            formatearBotonRespuesta(btnRespuestaC, "C", p.getOpciones()[2]);
            formatearBotonRespuesta(btnRespuestaD, "D", p.getOpciones()[3]);
            
            NumberFormat format = NumberFormat.getInstance(new Locale("es", "ES"));
            lblDinero.setText("Dinero: " + format.format(logica.getDineroActual()) + " €");
            
            panelProgreso.setNivelActual(indiceActual); 
        } else {
            ConexionMongo mongo = new ConexionMongo();
            mongo.guardarPartida(nombreJugador, 1000000);
            mostrarMensaje("¡VICTORIA ABSOLUTA!", "\n¡ENHORABUENA!\n\nHas acertado las 15 preguntas. ¡ERES MILLONARIO!", 2);
        }
    }

    // =========================================================================
    // LÓGICA DE COMPROBACIÓN CON TRANSICIÓN AUTOMÁTICA (Sin ventana de acierto)
    // =========================================================================
    private void comprobarRespuesta(int opcion) {
        if (bloqueado) return; // Evita que pulsen otra opción mientras se procesa la actual
        
        Pregunta pActual = listaPreguntas.get(indiceActual);
        boolean esCorrecta = logica.comprobarRespuesta(pActual, opcion);
        
        bloqueado = true;
        actualizarColoresRespuestas(opcion, esCorrecta);

        if (esCorrecta) {
            // Transición automática: Mantiene el color verde 1.2 segundos y pasa a la siguiente pregunta
            javax.swing.Timer timer = new javax.swing.Timer(1200, e -> {
                indiceActual++;
                actualizarTablero();
                bloqueado = false;
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            int premioConsolacion = logica.getDineroSiFalla();
            ConexionMongo mongo = new ConexionMongo();
            mongo.guardarPartida(nombreJugador, premioConsolacion);
            
            mostrarMensaje("¡RESPUESTA INCORRECTA!", "\n¡Fallaste!\n\nTe llevas el premio de seguridad acumulado: " + premioConsolacion + " €", 2);
            bloqueado = false;
        }
    }

    // =========================================================================
    // CLASES INTERNAS DE DIBUJADO
    // =========================================================================

    class BotonConcursillo extends JButton {
        private static final long serialVersionUID = 1L;
        private Color colorFondo, colorBorde, colorHover;
        private boolean isHovered = false;
        private EstadoRespuesta estado = EstadoRespuesta.NEUTRO; 

        public BotonConcursillo(String text, Color fondo, Color borde, Color hover) {
            super(text);
            this.colorFondo = fondo;
            this.colorBorde = borde;
            this.colorHover = hover;
            setFont(new Font("Tahoma", Font.BOLD, 15));
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    if (isEnabled() && estado == EstadoRespuesta.NEUTRO && !bloqueado) { 
                        isHovered = true; repaint(); 
                    }
                }
                public void mouseExited(MouseEvent e) {
                    if (isEnabled() && estado == EstadoRespuesta.NEUTRO) {
                        isHovered = false; repaint(); 
                    }
                }
            });
        }

        public void setEstado(EstadoRespuesta estado) {
            this.estado = estado;
            this.isHovered = false; 
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color bg, borderCol;
            
            if (!isEnabled()) {
                bg = new Color(25, 28, 35); 
                borderCol = new Color(60, 60, 70);
            } else {
                switch (estado) {
                    case ACIERTO:
                        bg = new Color(0, 160, 0, 240);
                        borderCol = new Color(0, 255, 0);
                        break;
                    case FALLO:
                        bg = new Color(180, 0, 0, 240);
                        borderCol = new Color(255, 50, 50);
                        break;
                    case NEUTRO:
                    default:
                        bg = (isHovered ? colorHover : colorFondo);
                        borderCol = (isHovered ? new Color(0, 200, 255) : colorBorde);
                        break;
                }
            }

            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());

            g2.setColor(borderCol);
            g2.setStroke(new BasicStroke(2.0f));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, getHeight(), getHeight());

            g2.dispose();
            super.paintComponent(g); 
        }
    }

    class PanelPreguntaConcursillo extends JPanel {
        private static final long serialVersionUID = 1L;

        public PanelPreguntaConcursillo() {
            setOpaque(false);
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(new Color(10, 12, 20, 240)); 
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
            
            g2.setColor(new Color(0, 140, 255)); 
            g2.setStroke(new BasicStroke(2.0f));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, getHeight(), getHeight());
            
            g2.dispose();
            super.paintComponent(g);
        }
    }

    class PanelProgreso extends JPanel {
        private static final long serialVersionUID = 1L;
        private int nivelActual = 0;
        private final int TOTAL_PREGUNTAS = 15;

        public PanelProgreso() {
            setOpaque(false);
        }

        public void setNivelActual(int nivel) {
            this.nivelActual = nivel;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int radio = 26; 
            int yCentro = height / 2;
            
            int margenHorizontal = 25;
            int espacioDisponible = width - (2 * margenHorizontal);
            int paso = espacioDisponible / (TOTAL_PREGUNTAS - 1);

            g2.setColor(new Color(40, 45, 60)); 
            g2.setStroke(new BasicStroke(3.0f));
            g2.drawLine(margenHorizontal, yCentro, margenHorizontal + espacioDisponible, yCentro);

            if (nivelActual > 0) {
                g2.setColor(new Color(30, 180, 80)); 
                int xProgreso = margenHorizontal + (nivelActual * paso);
                g2.drawLine(margenHorizontal, yCentro, Math.min(xProgreso, width - margenHorizontal), yCentro);
            }

            g2.setFont(new Font("Tahoma", Font.BOLD, 12));
            FontMetrics fm = g2.getFontMetrics();

            for (int i = 0; i < TOTAL_PREGUNTAS; i++) {
                int cx = margenHorizontal + (i * paso);
                int xEsquina = cx - (radio / 2);
                int yEsquina = yCentro - (radio / 2);

                if (i < nivelActual) {
                    g2.setColor(new Color(30, 180, 80));
                    g2.fillOval(xEsquina, yEsquina, radio, radio);
                    g2.setColor(Color.WHITE);
                } else if (i == nivelActual) {
                    g2.setColor(new Color(235, 60, 30));
                    g2.fillOval(xEsquina - 2, yEsquina - 2, radio + 4, radio + 4); 
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(2.0f));
                    g2.drawOval(xEsquina - 2, yEsquina - 2, radio + 4, radio + 4);
                } else {
                    g2.setColor(new Color(25, 28, 40)); 
                    g2.fillOval(xEsquina, yEsquina, radio, radio);
                    g2.setColor(new Color(100, 105, 120)); 
                }

                String numero = String.valueOf(i + 1);
                int anchoTexto = fm.stringWidth(numero);
                int altoTexto = fm.getAscent();
                g2.drawString(numero, cx - (anchoTexto / 2), yCentro + (altoTexto / 2) - 1);
            }
            g2.dispose();
        }
    }
}