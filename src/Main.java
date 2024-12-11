public class Main {
    public static void main(String[] args) {
        // Charger le fichier
        World world = new World("./data/airport-codes_no_comma.csv");

        // Afficher le nombre d'aéroports chargés
        System.out.println("Nombre d'aéroports chargés : " + world.getList().size());

        // Trouver un aéroport par code IATA
        Aeroport cdg = world.findByCode("CDG");
        if (cdg != null) {
            System.out.println("Aéroport trouvé par code IATA : " + cdg);
        } else {
            System.out.println("Aucun aéroport trouvé avec le code IATA 'CDG'.");
        }

        // Trouver l'aéroport le plus proche de Paris (latitude : 48.866, longitude : 2.316)
        Aeroport nearest = world.findNearest(2.316, 48.866);
        if (nearest != null) {
            System.out.println("Aéroport le plus proche de Paris : " + nearest);
        } else {
            System.out.println("Aucun aéroport trouvé proche de Paris.");
        }
    }
}
