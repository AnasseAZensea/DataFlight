import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // Lire le contenu de test.txt
            String jsonData = new String(Files.readAllBytes(Paths.get("./data/test.txt")));

            // Charger le fichier test.txt
            JsonFlightFiller filler = new JsonFlightFiller(jsonData);

            // Afficher tous les vols
            filler.displayFlights();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
