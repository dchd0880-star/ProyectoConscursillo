import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class BaseDatosLocal {
    
    // Tu cadena de conexión de Atlas
    private final String URI = "mongodb+srv://dchd0880_db_user:BFyNiEnlQMgVeVtR@proyectoconcursillo.2z703nl.mongodb.net/";

    public ArrayList<Pregunta> cargarPreguntas() {
        ArrayList<Pregunta> lista = new ArrayList<>();
        
        try (MongoClient mongoClient = MongoClients.create(URI)) {
            MongoDatabase database = mongoClient.getDatabase("ProyectoConcursillo");
            MongoCollection<Document> collection = database.getCollection("preguntas");

            // Leemos todos los documentos de la colección "preguntas"
            for (Document doc : collection.find()) {
            	String enunciado = doc.getString("enunciado");
            	List<String> opcionesList = (List<String>) doc.get("opciones");
            	String[] opciones = opcionesList.toArray(new String[0]);

            	// OJO AQUÍ: Asegúrate de que en Mongo el campo sea "correcta" (minúsculas)
            	int correcta = doc.getInteger("correcta"); 
            	int nivel = doc.getInteger("nivel");

            	// Creamos el objeto usando tus variables locales
            	lista.add(new Pregunta(enunciado, opciones, correcta, nivel));
            }
            System.out.println("Preguntas cargadas con éxito desde Atlas.");
        } catch (Exception e) {
            System.err.println("Error al cargar preguntas: " + e.getMessage());
        }
        System.out.println("DEBUG: He cargado " + lista.size() + " preguntas.");
        return lista;
    }
}