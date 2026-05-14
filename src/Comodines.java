// Clase destinada a gestionar la disponibilidad y algunas mecánicas de los comodines del juego
public class Comodines {
  
    // --- ATRIBUTOS DE ESTADO ---
    // Banderas (flags) booleanas que indican si cada comodín está listo para usarse (true)
    // o si ya ha sido consumido por el jugador durante la partida (false).
    public boolean disponible5050 = true;       // Estado del comodín del 50%
    public boolean disponibleChat = true;       // Estado del comodín del Chat / Público
    public boolean disponibleLlamada = true;    // Estado del comodín de la Llamada telefónica
    public boolean disponibleMago = true;       // Estado del comodín extra del Mago
    public boolean disponibleSacrificio = true; // Estado del comodín extra del Sacrificio

    // --- MÉTODOS DE LÓGICA ---

    // Método que simula el giro del comodín de la "Ruleta"
    // Devuelve la cantidad de comodines gastados que el jugador va a recuperar.
    public int usarRuleta() {
        // Genera un número entero aleatorio entre 0 y 3.
        // Math.random() devuelve un decimal [0.0, 1.0), al multiplicar por 4 da [0.0, 4.0),
        // y el casting a (int) trunca los decimales dejando los posibles resultados en: 0, 1, 2 o 3.
        int resultado = (int) (Math.random() * 4);
        
        // Retorna el número de comodines a reactivar
        return resultado;
    }
    
    // Método para registrar el consumo del comodín del "Mago"
    public void usarMago() {
        // Pasa el estado del comodín a 'false' para evitar que se pueda reutilizar
        this.disponibleMago = false;
    }
}