import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Date;

public class ConexionMongo {

    private final String URI = "mongodb+srv://dchd0880_db_user:BFyNiEnlQMgVeVtR@proyectoconcursillo.2z703nl.mongodb.net/";

    public void guardarPartida(String nombre, int dinero) {
        
    	try (MongoClient mongoClient = MongoClients.create(URI)) {
            
    		MongoDatabase database = mongoClient.getDatabase("ProyectoConcursillo");
            MongoCollection<Document> coleccion = database.getCollection("ranking");

            Document partida = new Document("jugador", nombre)
                                .append("premio", dinero)
                                .append("fecha", new Date());
            
            coleccion.insertOne(partida);
            System.out.println("Partida de " + nombre + " guardada en el Ranking de Atlas.");
            
        } catch (Exception e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
    }
}