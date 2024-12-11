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

        // Gestion du zoom avec la souris
        scene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                mousePosY = event.getSceneY(); // Capture de la position initiale de la souris
            }
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                double deltaY = event.getSceneY() - mousePosY; // Calcul du déplacement sur l'axe Y
                camera.setTranslateZ(camera.getTranslateZ() + deltaY * 0.1); // Mise à jour de la position de la caméra
                mousePosY = event.getSceneY(); // Mise à jour de la position actuelle de la souris
            }
        });

        // Gestion du clic droit pour afficher les coordonnées et l'aéroport le plus proche
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) { // Vérifier si c'est un clic droit
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
                            earth.displayRedSphere(nearestAirport); // Afficher une sphère rouge
                        }
                    }
                }
            }
        });

        // Associer la scène au stage
        primaryStage.setScene(scene);

        // Afficher la fenêtre
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
