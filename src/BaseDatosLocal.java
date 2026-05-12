import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseDatosLocal {
    
  
    private final String URI = "mongodb+srv://dchd0880_db_user:BFyNiEnlQMgVeVtR@proyectoconcursillo.2z703nl.mongodb.net/";

    public ArrayList<Pregunta> cargarPreguntas() {
    	ArrayList<Pregunta> lista = new ArrayList<>();
        
        try (MongoClient mongoClient = MongoClients.create(URI)) {
            MongoDatabase database = mongoClient.getDatabase("ProyectoConcursillo");
            MongoCollection<Document> collection = database.getCollection("preguntas");

         
            List<Document> pipeline = Arrays.asList(new Document("$sample", new Document("size", 16)));
            AggregateIterable<Document> resultados = collection.aggregate(pipeline);

            for (Document doc : resultados) {
                String enunciado = doc.getString("enunciado");
                
                List<String> opcionesList = (List<String>) doc.get("opciones");
                String[] opciones = opcionesList.toArray(new String[0]);
                
                int correcta = doc.getInteger("correcta");
                int nivel = doc.getInteger("nivel");

                lista.add(new Pregunta(enunciado, opciones, correcta, nivel));
            }
            
            System.out.println("Partida lista con " + lista.size() + " preguntas aleatorias.");
            
        } catch (Exception e) {
            System.err.println("Error al cargar preguntas: " + e.getMessage());
        }

        return lista;
    }
}