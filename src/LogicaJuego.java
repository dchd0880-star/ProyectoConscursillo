// Clase que gestiona el estado interno de la partida y las reglas económicas del concurso
public class LogicaJuego {
	
    // Atributo que rastrea el nivel o ronda en la que se encuentra el jugador (inicia en 1)
    private int nivelActual = 1;
    
    // Escalera de premios oficial del juego almacenada en un array.
    // El índice 0 equivale al inicio (0€) y va subiendo hasta la pregunta 15 (1.000.000€).
    private int[] premios = {0, 100, 250, 500, 750, 1500, 2500, 5000, 10000, 15000, 20000, 30000, 50000, 100000, 300000, 1000000};

    // Método que evalúa si la opción seleccionada por el jugador coincide con la respuesta correcta
    public boolean comprobarRespuesta(Pregunta p, int opcionElegida) {
        // Compara el índice de la respuesta correcta del objeto Pregunta con el botón pulsado (0 a 3)
        if (p.getRespuestaCorrecta() == opcionElegida) {
            // Si el jugador acierta, sube de nivel automáticamente
            nivelActual++;
            return true; // Retorna verdadero para confirmar el acierto a la interfaz
        }
        // Si no coinciden, retorna falso indicando que ha fallado
        return false;
    }

    // Método que devuelve la cantidad de dinero acumulada en el nivel actual
    public int getDineroActual() {
        // Restamos 1 al nivelActual para alinear el nivel del juego (1-16) con los índices del array (0-15)
        return premios[nivelActual - 1];
    }

    // Método que calcula el premio de consolación en caso de fallo,
    // aplicando los "niveles de seguridad" (puntos de control) definidos en las normas.
    public int getDineroSiFalla() {
        // Segundo nivel de seguridad: Si ya superó la pregunta 10, tiene garantizados 20.000€
        if (nivelActual > 10) return 20000;
        
        // Primer nivel de seguridad: Si ya superó la pregunta 5, tiene garantizados 1.500€
        if (nivelActual > 5) return 1500;
        
        // Si falla antes de llegar a la pregunta 6, se va con las manos vacías
        return 0;
    }
    
    // Método getter para consultar en qué número de pregunta va el jugador
    public int getNivelActual() { 
    	return nivelActual; 
    }
}