import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JPanel panelReglas;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuPrincipal() {
		setTitle("El Concursillo");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/imagenes/IconoPrincipal.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 924, 692);
		setLocationRelativeTo(null); // Centra la ventana en la pantalla
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(187, 119, 255));
		contentPane.setForeground(new Color(187, 119, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// ==========================================
		// PANEL DE REGLAS INTERNO (Oculto al inicio)
		// ==========================================
		panelReglas = new JPanel();
		panelReglas.setBounds(160, 150, 600, 350);
		panelReglas.setBackground(new Color(40, 20, 70)); // Morado oscuro elegante
		panelReglas.setBorder(new LineBorder(Color.WHITE, 3, true));
		panelReglas.setLayout(null);
		panelReglas.setVisible(false); // Empieza oculto
		contentPane.add(panelReglas);
		
		JLabel lblTituloReglas = new JLabel("REGLAS DE EL CONCURSILLO");
		lblTituloReglas.setForeground(Color.WHITE);
		lblTituloReglas.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTituloReglas.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloReglas.setBounds(10, 20, 580, 35);
		panelReglas.add(lblTituloReglas);
		
		JTextArea txtReglas = new JTextArea();
		txtReglas.setText("\n• Responde 15 preguntas correctamente para ganar el millón.\n\n" +
		                  "• Niveles de seguridad: 1.500€ (pregunta 5) y 20.000€ (pregunta 10).\n\n" +
		                  "• Si fallas, caes al último nivel de seguridad alcanzado.\n\n" +
		                  "• Puedes plantarte en cualquier momento y llevarte el dinero acumulado.");
		txtReglas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtReglas.setForeground(Color.WHITE);
		txtReglas.setBackground(new Color(40, 20, 70));
		txtReglas.setEditable(false);
		txtReglas.setFocusable(false);
		txtReglas.setLineWrap(true);
		txtReglas.setWrapStyleWord(true);
		txtReglas.setBounds(40, 75, 520, 200);
		panelReglas.add(txtReglas);
		
		JButton btnCerrarReglas = new JButton("Entendido");
		btnCerrarReglas.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCerrarReglas.setBackground(Color.WHITE);
		btnCerrarReglas.setForeground(new Color(40, 20, 70));
		btnCerrarReglas.setBounds(230, 285, 140, 40);
		btnCerrarReglas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelReglas.setVisible(false); // Oculta el panel al hacer clic
			}
		});
		panelReglas.add(btnCerrarReglas);
		
		// Aseguramos que el panel de reglas se dibuje por encima de todo lo demás
		contentPane.setComponentZOrder(panelReglas, 0);

		// ==========================================
		// RESTO DE COMPONENTES DE LA INTERFAZ
		// ==========================================
		txtNombre = new JTextField();
		txtNombre.setBounds(690, 177, 202, 33);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre Jugador");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setFont(new Font("Sylfaen", Font.BOLD, 17));
		lblNombre.setBounds(680, 148, 218, 33);
		contentPane.add(lblNombre);
		
		JButton btnNuevaPartida = new JButton("");
		btnNuevaPartida.setSelectedIcon(null);
		// Usamos tu imagen original
		btnNuevaPartida.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonEmpezar2.png")));
		btnNuevaPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String nombreUsuario = txtNombre.getText();

			    if (nombreUsuario.trim().isEmpty()) {
			        javax.swing.JOptionPane.showMessageDialog(null, "¡Illo, pon tu nombre para jugar!");
			    } else {
		            LogicaJuego miLogica = new LogicaJuego();
		            PantallaJuego ventanaJuego = new PantallaJuego(miLogica, nombreUsuario);
		            ventanaJuego.setVisible(true);
		            dispose(); 
		        }
		    }
		});
		btnNuevaPartida.setBounds(52, 428, 495, 79);
		// Quitamos bordes y fondos por defecto para que la imagen del botón luzca limpia
		btnNuevaPartida.setContentAreaFilled(false);
		btnNuevaPartida.setBorderPainted(false);
		btnNuevaPartida.setFocusPainted(false);
		contentPane.add(btnNuevaPartida);
		
		JButton btnRanking = new JButton("");
		btnRanking.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonRanking2.png")));
		btnRanking.setBounds(132, 537, 324, 63);
		btnRanking.setContentAreaFilled(false);
		btnRanking.setBorderPainted(false);
		btnRanking.setFocusPainted(false);
		contentPane.add(btnRanking);
		
		JButton btnInfo = new JButton("");
		btnInfo.setBackground(new Color(255, 255, 255));
		btnInfo.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonInfo.png")));
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Al pulsar Info, mostramos nuestro panel interno superpuesto
				panelReglas.setVisible(true);
			}
		});
		btnInfo.setBounds(680, 11, 218, 126);
		btnInfo.setContentAreaFilled(false);
		btnInfo.setBorderPainted(false);
		btnInfo.setFocusPainted(false);
		contentPane.add(btnInfo);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/LetrasApp.png")));
		lblNewLabel_1.setBounds(29, 26, 518, 361);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/Illojuan .png")));
		lblNewLabel.setBounds(574, 247, 602, 463);
		contentPane.add(lblNewLabel);
	}
}
