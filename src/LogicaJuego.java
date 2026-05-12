
public class LogicaJuego {
    private int nivelActual = 1;
    private int[] premios = {0, 100, 250, 500, 750, 1500, 2500, 5000, 10000, 15000, 20000, 30000, 50000, 100000, 300000, 1000000};

    public boolean comprobarRespuesta(Pregunta p, int opcionElegida) {
        if (p.getRespuestaCorrecta() == opcionElegida) {
            nivelActual++;
            return true;
        }
        return false;
    }

    public int getDineroActual() {
        return premios[nivelActual - 1];
    }

    public int getDineroSiFalla() {
        if (nivelActual > 10) return 20000;
        if (nivelActual > 5) return 1500;
        return 0;
    }
    
    public int getNivelActual() { return nivelActual; }
}