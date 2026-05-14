import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Date;

// Clase encargada de gestionar la persistencia de los resultados finales en la nube
public class ConexionMongo {

    // Cadena de conexión (URI) para conectar con el clúster remoto de MongoDB Atlas
    private final String URI = "mongodb+srv://dchd0880_db_user:BFyNiEnlQMgVeVtR@proyectoconcursillo.2z703nl.mongodb.net/";

    // Método que registra la puntuación final de un jugador en la tabla de clasificación mundial.
    // Recibe el nombre del jugador y la cantidad total de dinero con la que ha terminado la partida.
    public void guardarPartida(String nombre, int dinero) {
        
        // Usamos un bloque try-with-resources para garantizar que la conexión con el clúster
        // se abra y se cierre automáticamente de forma segura al terminar la operación de escritura.
    	try (MongoClient mongoClient = MongoClients.create(URI)) {
            
            // Conectamos con la base de datos principal del proyecto
    		MongoDatabase database = mongoClient.getDatabase("ProyectoConcursillo");
            // Seleccionamos la colección "ranking", donde se guardan las puntuaciones históricas
            MongoCollection<Document> coleccion = database.getCollection("ranking");

            // --- CREACIÓN DEL DOCUMENTO ---
            // Creamos un nuevo documento BSON (el formato nativo de pares clave-valor de MongoDB)
            // y mapeamos los datos de la partida: el nombre del jugador, el premio final y la fecha/hora actual.
            Document partida = new Document("jugador", nombre)
                                .append("premio", dinero)
                                .append("fecha", new Date());
            
            // Insertamos el documento recién creado en la colección remota de Atlas
            coleccion.insertOne(partida);
            
            // Confirmación por consola de que el guardado en la nube se ha completado con éxito
            System.out.println("Partida de " + nombre + " guardada en el Ranking de Atlas.");
            
        } catch (Exception e) {
            // Captura y muestra por la salida de error cualquier fallo de red, autenticación o escritura
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
    }
}