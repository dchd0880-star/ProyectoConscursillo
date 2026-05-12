import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JButton btnNuevaPartida;
	private JButton btnRanking;
	private JButton btnInfo;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				MenuPrincipal frame = new MenuPrincipal();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public MenuPrincipal() {
		setTitle("El Concursillo");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/imagenes/IconoPrincipal.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 924, 692);
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
		// COMPONENTES DE ENTRADA (Nombre Jugador estilizado)
		// ====================================================
		JLabel lblNombre = new JLabel("Nombre Jugador");
		lblNombre.setForeground(new Color(150, 255, 255)); 
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNombre.setBounds(670, 140, 242, 30);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBackground(new Color(15, 15, 30));
		txtNombre.setForeground(Color.WHITE);
		txtNombre.setCaretColor(Color.WHITE);
		txtNombre.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombre.setBorder(new LineBorder(new Color(0, 120, 255), 2, true));
		txtNombre.setBounds(690, 177, 202, 35);
		contentPane.add(txtNombre);
		
		// ====================================================
		// BOTONES ORIGINALES CON SUS IMÁGENES (Sin fondos nativos)
		// ====================================================
		btnNuevaPartida = new JButton("");
		btnNuevaPartida.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonEmpezar2.png")));
		btnNuevaPartida.setBounds(52, 428, 495, 79);
		btnNuevaPartida.setContentAreaFilled(false);
		btnNuevaPartida.setBorderPainted(false);
		btnNuevaPartida.setFocusPainted(false);
		btnNuevaPartida.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnNuevaPartida.addActionListener(e -> empezarPartida());
		contentPane.add(btnNuevaPartida);
		
		btnRanking = new JButton("");
		btnRanking.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonRanking2.png")));
		btnRanking.setBounds(132, 537, 324, 63);
		btnRanking.setContentAreaFilled(false);
		btnRanking.setBorderPainted(false);
		btnRanking.setFocusPainted(false);
		btnRanking.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(btnRanking);
		
		btnInfo = new JButton("");
		btnInfo.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonInfo.png")));
		btnInfo.setBounds(680, 11, 218, 126);
		btnInfo.setContentAreaFilled(false);
		btnInfo.setBorderPainted(false);
		btnInfo.setFocusPainted(false);
		btnInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnInfo.addActionListener(e -> {
			String reglas = "\n• Responde 15 preguntas correctamente para ganar el millón.\n\n" +
							"• Niveles de seguridad: 1.500€ (pregunta 5) y 20.000€ (pregunta 10).\n\n" +
							"• Si fallas, caes al último nivel de seguridad alcanzado.\n\n" +
							"• Puedes plantarte en cualquier momento y llevarte el dinero acumulado.";
			mostrarMensaje("REGLAS DE EL CONCURSILLO", reglas);
		});
		contentPane.add(btnInfo);
		
		// ====================================================
		// IMÁGENES DECORATIVAS
		// ====================================================
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/LetrasApp.png")));
		lblLogo.setBounds(29, 20, 518, 360);
		contentPane.add(lblLogo);
		
		JLabel lblJuan = new JLabel("");
		lblJuan.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/Illojuan .png")));
		lblJuan.setBounds(574, 240, 602, 460);
		contentPane.add(lblJuan);
	}

	// =========================================================================
	// LÓGICA DE ARRANQUE CON SWINGWORKER
	// =========================================================================
	private void empezarPartida() {
		String nombreUsuario = txtNombre.getText().trim();

		if (nombreUsuario.isEmpty()) {
			mostrarMensaje("¡ATENCIÓN!", "\n¡Illo, pon tu nombre para poder jugar!");
		} else {
			// Desactivamos el botón temporalmente para evitar dobles clics
			btnNuevaPartida.setEnabled(false);

			SwingWorker<PantallaJuego, Void> worker = new SwingWorker<PantallaJuego, Void>() {
				@Override
				protected PantallaJuego doInBackground() throws Exception {
					LogicaJuego miLogica = new LogicaJuego();
					return new PantallaJuego(miLogica, nombreUsuario);
				}

				@Override
				protected void done() {
					try {
						PantallaJuego ventanaJuego = get();
						ventanaJuego.setVisible(true);
						dispose(); 
					} catch (Exception ex) {
						btnNuevaPartida.setEnabled(true);
						mostrarMensaje("ERROR DE CONEXIÓN", "\nNo se pudo conectar a la base de datos.\n\nDetalle: " + ex.getMessage());
					}
				}
			};
			worker.execute();
		}
	}

	// =========================================================================
	// SISTEMA MODAL DE AVISOS CON ESQUINAS REDONDEADAS
	// =========================================================================
	private void mostrarMensaje(String titulo, String texto) {
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
		
		// Botón interno curvo dedicado exclusivamente a cerrar el mensaje
		BotonMenuDialogo btnOk = new BotonMenuDialogo("Aceptar", new Color(25, 30, 45), new Color(0, 150, 255));
		btnOk.setBounds(230, 275, 140, 45);
		btnOk.addActionListener(e -> dialogo.dispose());
		panelDialogo.add(btnOk);
		
		dialogo.setContentPane(panelDialogo);
		dialogo.setVisible(true);
	}

	// =========================================================================
	// CLASE INTERNA: BOTÓN REDONDEADO (Solo para el botón Aceptar de los avisos)
	// =========================================================================
	class BotonMenuDialogo extends JButton {
		private static final long serialVersionUID = 1L;
		private Color colorFondo, colorBorde;
		private boolean isHovered = false;

		public BotonMenuDialogo(String text, Color fondo, Color borde) {
			super(text);
			this.colorFondo = fondo;
			this.colorBorde = borde;
			setFont(new Font("Tahoma", Font.BOLD, 18));
			setForeground(Color.WHITE);
			setContentAreaFilled(false);
			setFocusPainted(false);
			setBorderPainted(false);
			setCursor(new Cursor(Cursor.HAND_CURSOR));

			addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) { if (isEnabled()) { isHovered = true; repaint(); } }
				public void mouseExited(MouseEvent e) { if (isEnabled()) { isHovered = false; repaint(); } }
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			Color bg = !isEnabled() ? new Color(25, 28, 35) : (isHovered ? colorFondo.brighter() : colorFondo);
			Color brd = !isEnabled() ? new Color(60, 60, 70) : (isHovered ? new Color(0, 200, 255) : colorBorde);

			g2.setColor(bg);
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());

			g2.setColor(brd);
			g2.setStroke(new BasicStroke(2.0f));
			g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, getHeight(), getHeight());

			g2.dispose();
			super.paintComponent(g); 
		}
	}
}