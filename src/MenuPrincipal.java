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
	private JLabel lblError;
	
	
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
		contentPane = new JPanel();
		contentPane.setBackground(new Color(187, 119, 255));
		contentPane.setForeground(new Color(187, 119, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblError.setBounds(10, 50, 200, 20); // Ajusta las coordenadas a tu diseño
		contentPane.add(lblError);
		
		JButton btnNuevaPartida = new JButton("Empezar Partida");
		btnNuevaPartida.setSelectedIcon(null);
		btnNuevaPartida.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonEmpezar2.png")));
		btnNuevaPartida.addActionListener(new ActionListener() {
			
			
			
			public void actionPerformed(ActionEvent e) {
			    String nombreUsuario = txtNombre.getText();

			    if (nombreUsuario.trim().isEmpty()) {
			    	lblError.setText("¡Illo, pon tu nombre para jugar!");
			    } else {
		         
		            LogicaJuego miLogica = new LogicaJuego();
		            
		         
		            PantallaJuego ventanaJuego = new PantallaJuego(miLogica, nombreUsuario);
		            
		    
		            ventanaJuego.setVisible(true);
		    
		            dispose(); 
		        }
		    }
		});
		btnNuevaPartida.setBounds(52, 428, 495, 79);
		contentPane.add(btnNuevaPartida);
		
		JButton btnRanking = new JButton("");
		btnRanking.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonRanking2.png")));
		btnRanking.setBounds(132, 537, 324, 63);
		contentPane.add(btnRanking);
		
		JButton btnInfo = new JButton("");
		btnInfo.setBackground(new Color(255, 255, 255));
		btnInfo.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonInfo.png")));
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
		btnInfo.setBounds(680, 11, 218, 126);
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
