import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class World {
    private ArrayList<Aeroport> list = new ArrayList<>();

    // Getter pour la liste des aéroports
    public ArrayList<Aeroport> getList() {
        return list;
    }

    // Constructeur pour charger les données depuis le fichier CSV
    public World(String fileName) {
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String line = buf.readLine(); // Lire la première ligne (en-tête)
            while ((line = buf.readLine()) != null) {
                line = line.replaceAll("\"", ""); // Supprimer les guillemets
                String[] fields = line.split(",");

                // Vérifier si c'est un large_airport avant d'ajouter à la liste
                if (fields[1].equals("large_airport")) {
                    list.add(new Aeroport(
                            fields[2],               // Nom
                            fields[9],               // Code IATA
                            Double.parseDouble(fields[12]), // Latitude
                            Double.parseDouble(fields[11]), // Longitude
                            fields[5]                // Pays
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    // Trouver un aéroport par son code IATA
    public Aeroport findByCode(String iata) {
        for (Aeroport aeroport : list) {
            if (aeroport.getIATA().equalsIgnoreCase(iata)) {
                return aeroport;
            }
        }
        return null; // Retourner null si aucun aéroport trouvé
    }

    // Trouver l'aéroport le plus proche d'une position donnée
    public Aeroport findNearest(double longitude, double latitude) {
        Aeroport nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Aeroport aeroport : list) {
            double distance = aeroport.calculDistance(new Aeroport("Ref", "", latitude, longitude, ""));
            if (distance < minDistance) {
                nearest = aeroport;
                minDistance = distance;
            }
        }
        return nearest;
    }
}
