import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.stage.Stage;

public class Interface extends Application {
    private double mousePosY; // Position de la souris sur l'axe Y

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Terre 3D");

        // Instancier un objet Earth
        Earth earth = new Earth();

        // Charger les données d'aéroports
        World world = new World("./data/airport-codes_no_comma.csv");

        // Créer une scène avec l'objet Earth
        Scene scene = new Scene(earth, 800, 600, true);

        // Ajouter une caméra à la scène
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000); // Reculer la caméra pour voir la sphère
        camera.setNearClip(0.1);     // Distance minimale de rendu
        camera.setFarClip(2000.0);   // Distance maximale de rendu
        camera.setFieldOfView(35);   // Champ de vision
        scene.setCamera(camera);

        // Gestion des clics sur la Terre
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) { // Clic droit
                PickResult pickResult = event.getPickResult();
                if (pickResult.getIntersectedNode() != null) {
                    Point2D click = pickResult.getIntersectedTexCoord();
                    if (click != null) {
                        double longitude = 360 * (click.getX() - 0.5);
                        double latitude = 180 * (0.5 - click.getY());

                        System.out.println("Longitude : " + longitude + ", Latitude : " + latitude);

                        Aeroport nearestAirport = world.findNearest(longitude, latitude);
                        if (nearestAirport != null) {
                            System.out.println("Aéroport le plus proche : " + nearestAirport);
                            earth.displayRedSphere(nearestAirport); // Affiche une sphère rouge

                            // Création d'une URL dynamique en fonction de l'aéroport
                            String apiUrl = "http://api.aviationstack.com/v1/flights?access_key=5baf16ec3b0516ba36fc8c2b17cfd7ac&arr_iata=" + nearestAirport.getIATA();

                            // Création d'une instance de HTTPSRunnable
                            HTTPSRunnable runnable = new HTTPSRunnable(apiUrl, earth, world);

                            // Création d'un thread qui va exécuter la requête API
                            Thread thread = new Thread(runnable);
                            thread.start(); // Lancement du thread
                        }
                    }
                }
            }
        });

        // Associer la scène au stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
