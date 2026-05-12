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

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public MenuPrincipal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/imagenes/IconoPrincipal.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 924, 692);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(187, 119, 255));
		contentPane.setForeground(new Color(187, 119, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(342, 487, 202, 33);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setFont(new Font("Sylfaen", Font.PLAIN, 17));
		lblNombre.setBounds(331, 455, 218, 33);
		contentPane.add(lblNombre);
		
		JButton btnNuevaPartida = new JButton("Empezar Paritda");
		btnNuevaPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String nombreUsuario = txtNombre.getText();

			    if (nombreUsuario.trim().isEmpty()) {
			        javax.swing.JOptionPane.showMessageDialog(null, "¡Illo, pon tu nombre para jugar!");
			    } else {
		            // 2. Creamos la lógica que ya tienes definida
		            LogicaJuego miLogica = new LogicaJuego();
		            
		            // 3. CREAMOS EL NUEVO FRAME y le pasamos los datos
		            PantallaJuego ventanaJuego = new PantallaJuego(miLogica, nombreUsuario);
		            
		            // 4. HACEMOS VISIBLE LA NUEVA VENTANA
		            ventanaJuego.setVisible(true);
		            
		            // 5. CERRAMOS EL MENÚ ACTUAL (this es el MenuPrincipal)
		            dispose(); 
		        }
		    }
		});
		btnNuevaPartida.setBounds(273, 559, 352, 46);
		contentPane.add(btnNuevaPartida);
		
		JButton btnRanking = new JButton("Ver Ranking");
		btnRanking.setBounds(647, 559, 226, 46);
		contentPane.add(btnRanking);
		
		JButton btnInfo = new JButton("Info Juego");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String reglas = "REGLAS DE EL CONCURSILLO:\n\n" +
			                    "- Responde 15 preguntas correctamente.\n" +
			                    "- Niveles de seguridad: 1.500€ (pregunta 5) y 20.000€ (pregunta 10).\n" +
			                    "- Si fallas, caes al último nivel de seguridad alcanzado.\n" +
			                    "- Puedes plantarte y llevarte el dinero acumulado.";
			    
			    javax.swing.JOptionPane.showMessageDialog(null, reglas, "Información del Juego", javax.swing.JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnInfo.setBounds(22, 559, 226, 46);
		contentPane.add(btnInfo);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/LetrasApp.png")));
		lblNewLabel_1.setBounds(173, 66, 518, 352);
		contentPane.add(lblNewLabel_1);

	}
}
