import java.util.ArrayList;

public class BaseDatosLocal {

    public ArrayList<Pregunta> cargarPreguntas() {
        ArrayList<Pregunta> lista = new ArrayList<>();

        lista.add(new Pregunta(
            "¿Cómo se llama el presentador de 'El Concursillo'?", 
            new String[]{"Ibai", "IlloJuan", "TheGrefg", "Rubius"}, 
            1, 
            1
        ));

        lista.add(new Pregunta(
            "¿Cuál es el valor más bajo de un billete de euro?", 
            new String[]{"Cinco", "Diez", "Quince", "Veinte"}, 
            0, 
            2
        ));

        return lista;
    }
}