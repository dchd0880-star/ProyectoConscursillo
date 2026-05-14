// Clase modelo que representa una pregunta individual dentro del juego
public class Pregunta {
	
    // Atributos privados que almacenan la información del documento traído de la base de datos
    private String enunciado;          // El texto de la pregunta (ej. "¿Cuál es la capital de Francia?")
    private String[] opciones;         // Array con las 4 posibles respuestas posibles (A, B, C, D)
    private int respuestaCorrecta;     // Índice numérico (0 a 3) que indica cuál de las opciones es la correcta
    private int nivel;                 // Nivel de dificultad asociado a la pregunta

    // Constructor de la clase: se ejecuta al instanciar un nuevo objeto Pregunta
    // Recibe los datos extraídos de MongoDB y los asigna a los atributos internos
    public Pregunta(String enunciado, String[] opciones, int respuestaCorrecta, int nivel) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.nivel = nivel;
    }

    // --- MÉTODOS GETTER ---
    // Métodos de acceso público para que otras clases (como PantallaJuego o LogicaJuego) 
    // puedan consultar la información encapsulada en el objeto sin poder modificarla.

    // Devuelve el texto del enunciado
    public String getEnunciado() { 
    	return enunciado; 
    }
    
    // Devuelve el array completo con las 4 opciones de respuesta
    public String[] getOpciones() { 
    	return opciones; 
    }
    
    // Devuelve el índice (0=A, 1=B, 2=C, 3=D) de la respuesta correcta
    public int getRespuestaCorrecta() { 
    	return respuestaCorrecta; 
    }
    
    // Devuelve la dificultad asignada a la pregunta
    public int getNivel() { 
    	return nivel; 
    }
}