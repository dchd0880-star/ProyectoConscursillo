import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Clase encargada de proveer las preguntas para la partida conectándose a la base de datos en la nube
public class BaseDatosLocal {
    
    // Cadena de conexión (URI) para acceder al clúster de MongoDB Atlas con usuario y contraseña
    private final String URI = "mongodb+srv://dchd0880_db_user:BFyNiEnlQMgVeVtR@proyectoconcursillo.2z703nl.mongodb.net/";

    // Método que extrae 16 preguntas aleatorias de la nube y las devuelve como una lista de objetos Java
    public ArrayList<Pregunta> cargarPreguntas() {
        // Inicializamos la lista donde guardaremos los objetos Pregunta generados
        ArrayList<Pregunta> lista = new ArrayList<>();
        
        // Bloque try-with-resources que asegura que la conexión al cliente Mongo se cierre automáticamente al terminar
        try (MongoClient mongoClient = MongoClients.create(URI)) {
            // Accedemos a la base de datos y a la colección específica donde residen las preguntas
            MongoDatabase database = mongoClient.getDatabase("ProyectoConcursillo");
            MongoCollection<Document> collection = database.getCollection("preguntas");

            // --- PIPELINE DE AGREGACIÓN ---
            // Utilizamos el operador "$sample" nativo de MongoDB para obtener una muestra puramente aleatoria.
            // Pedimos un tamaño ("size") de 16 documentos para cubrir las 15 rondas del juego más 1 de reserva (Mago).
            List<Document> pipeline = Arrays.asList(new Document("$sample", new Document("size", 16)));
            
            // Ejecutamos la consulta de agregación en la colección
            AggregateIterable<Document> resultados = collection.aggregate(pipeline);

            // Recorremos cada documento BSON devuelto por la base de datos
            for (Document doc : resultados) {
                // Extraemos el texto de la pregunta
                String enunciado = doc.getString("enunciado");
                
                // Extraemos el array de opciones (BSON Array) y lo convertimos a una Lista de Strings en Java
                @SuppressWarnings("unchecked")
                List<String> opcionesList = (List<String>) doc.get("opciones");
                // Transformamos la Lista en un Array clásico de Strings para compatibilidad con la clase Pregunta
                String[] opciones = opcionesList.toArray(new String[0]);
                
                // Extraemos los valores enteros: el índice de la respuesta correcta y la dificultad
                int correcta = doc.getInteger("correcta");
                int nivel = doc.getInteger("nivel");

                // Instanciamos un nuevo objeto Pregunta con los datos mapeados y lo añadimos al pool de la partida
                lista.add(new Pregunta(enunciado, opciones, correcta, nivel));
            }
            
            // Mensaje de depuración por consola para verificar que la carga ha sido exitosa
            System.out.println("Partida lista con " + lista.size() + " preguntas aleatorias.");
            
        } catch (Exception e) {
            // Captura e informa de cualquier problema de red, autenticación o de parseo de datos
            System.err.println("Error al cargar preguntas: " + e.getMessage());
        }

        // Retornamos el pool de preguntas listo para ser consumido por la interfaz del juego
        return lista;
    }
}