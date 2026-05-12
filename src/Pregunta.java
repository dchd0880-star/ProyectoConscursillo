public class Pregunta {
    private String enunciado;
    private String[] opciones;
    private int respuestaCorrecta;
    private int nivel;

    public Pregunta(String enunciado, String[] opciones, int respuestaCorrecta, int nivel) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.nivel = nivel;
    }

    public String getEnunciado() { return enunciado; }
    public String[] getOpciones() { return opciones; }
    public int getRespuestaCorrecta() { return respuestaCorrecta; }
    public int getNivel() { return nivel; }
}