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

// Clase principal que hereda de JFrame para crear la ventana del menú
public class MenuPrincipal extends JFrame {

	// Identificador de versión para la serialización de la clase
	private static final long serialVersionUID = 1L;
	
	// Declaración de los componentes de la interfaz gráfica
	private JPanel contentPane;
	private JTextField txtNombre;
	private JLabel lblError;
	
	// Método principal que arranca la aplicación
	public static void main(String[] args) {
		// Ejecuta la creación de la ventana en el hilo de eventos de Swing (hilo seguro)
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Instancia y hace visible la ventana del menú principal
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructor de la clase donde se inicializa y configura toda la interfaz
	public MenuPrincipal() {
		// Configuración básica de la ventana principal
		setTitle("El Concursillo");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/imagenes/IconoPrincipal.png")));
		setResizable(false); // Evita que el usuario redimensione la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra el proceso al pulsar la 'X'
		setBounds(100, 100, 924, 692); // Posición (x, y) y tamaño (ancho, alto) de la ventana
		
		// Creación y configuración del panel contenedor principal
		contentPane = new JPanel();
		contentPane.setBackground(new Color(187, 119, 255)); // Fondo morado
		contentPane.setForeground(new Color(187, 119, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null); // Posicionamiento absoluto desactivando el Layout Manager
		
		// Campo de texto para que el usuario introduzca su nombre
		txtNombre = new JTextField();
		txtNombre.setBounds(690, 177, 202, 33);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		// Etiqueta indicativa "Nombre Jugador" encima del campo de texto
		JLabel lblNombre = new JLabel("Nombre Jugador");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setFont(new Font("Sylfaen", Font.BOLD, 17));
		lblNombre.setBounds(680, 148, 218, 33);
		contentPane.add(lblNombre);
		
		// Etiqueta para mostrar mensajes de error de validación (vacía por defecto)
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblError.setBounds(10, 50, 200, 20); // Ajusta las coordenadas a tu diseño
		contentPane.add(lblError);
		
		// Creación del botón para iniciar una nueva partida
		JButton btnNuevaPartida = new JButton("Empezar Partida");
		btnNuevaPartida.setSelectedIcon(null);
		btnNuevaPartida.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonEmpezar2.png")));
		
		// Evento que se dispara al pulsar el botón de Empezar Partida
		btnNuevaPartida.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// Captura el texto escrito en el campo txtNombre
			    String nombreUsuario = txtNombre.getText();

			    // Validación: Comprueba si el texto está vacío (incluso si solo hay espacios)
			    if (nombreUsuario.trim().isEmpty()) {
			    	lblError.setText("¡Illo, pon tu nombre para jugar!");
			    } else {
		            // Si el nombre es válido, instancia la lógica principal del juego
		            LogicaJuego miLogica = new LogicaJuego();
		            
		            // Crea la nueva ventana del juego pasando la lógica y el nombre del usuario
		            PantallaJuego ventanaJuego = new PantallaJuego(miLogica, nombreUsuario);
		            
		            // Muestra la ventana del juego
		            ventanaJuego.setVisible(true);
		    
		            // Cierra y libera los recursos de la ventana actual (MenuPrincipal)
		            dispose(); 
		        }
		    }
		});
		btnNuevaPartida.setBounds(52, 428, 495, 79);
		contentPane.add(btnNuevaPartida);
		
		// Mejora de usabilidad: Permite iniciar el juego pulsando la tecla 'Enter'
				txtNombre.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Ejecuta automáticamente la acción del botón empezar
						btnNuevaPartida.doClick();
					}
				});
		
		// Creación del botón para acceder a la tabla de puntuaciones (Ranking)
		JButton btnRanking = new JButton("");
		
		// Evento que se dispara al pulsar el botón de Ranking
		btnRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Instancia y muestra la ventana de ranking
				PantallaRanking ventanaRanking = new PantallaRanking();
		        ventanaRanking.setVisible(true);
			}
		});
		btnRanking.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonRanking2.png")));
		btnRanking.setBounds(132, 537, 324, 63);
		contentPane.add(btnRanking);
		
		// Creación del botón de información (reglas del juego)
		JButton btnInfo = new JButton("");
		btnInfo.setBackground(new Color(255, 255, 255));
		btnInfo.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/ImagenesBotones/BotonInfo.png")));
		
		// Evento que se dispara al pulsar el botón de Información
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Texto con las normas del juego
			    String reglas = "REGLAS DE EL CONCURSILLO:\n\n" +
			                    "- Responde 15 preguntas correctamente.\n" +
			                    "- Niveles de seguridad: 1.500€ (pregunta 5) y 20.000€ (pregunta 10).\n" +
			                    "- Si fallas, caes al último nivel de seguridad alcanzado.\n" +
			                    "- Puedes plantarte y llevarte el dinero acumulado.";
			    
			    // Muestra una ventana emergente (diálogo) con el texto de las reglas
			    javax.swing.JOptionPane.showMessageDialog(null, reglas, "Información del Juego", javax.swing.JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnInfo.setBounds(680, 11, 218, 126);
		contentPane.add(btnInfo);
		
		// Imagen decorativa: Letras/Logo de la aplicación
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/LetrasApp.png")));
		lblNewLabel_1.setBounds(29, 26, 518, 361);
		contentPane.add(lblNewLabel_1);
		
		// Imagen decorativa: Render o imagen de IlloJuan
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/Illojuan .png")));
		lblNewLabel.setBounds(574, 247, 602, 463);
		contentPane.add(lblNewLabel);

	}
}