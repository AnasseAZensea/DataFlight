import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;

public class Earth extends Group {
    private Sphere sph; // La sphère principale (Terre)
    private Rotate ry;  // Rotation autour de l'axe Y

    public Earth() {
        // Créer une sphère de rayon 300
        sph = new Sphere(300);

        // Créer un matériau PhongMaterial
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("file:./data/earth_lights_4800.png"));
        sph.setMaterial(material);

        // Ajouter la sphère au groupe
        this.getChildren().add(sph);

        // Ajouter une rotation autour de l'axe Y
        ry = new Rotate(0, Rotate.Y_AXIS);
        this.getTransforms().add(ry);

        // Animation pour faire tourner la Terre
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double angle = (now / 1_000_000_000.0) * (360.0 / 15.0); // Un tour en 15 secondes
                ry.setAngle(angle % 360); // Limiter l'angle entre 0 et 360°
            }
        };
        animationTimer.start();
    }

    // Créer une sphère colorée sur un aéroport donné
    public Sphere createSphere(Aeroport a, Color color) {
        PhongMaterial col = new PhongMaterial();
        col.setDiffuseColor(color);
        col.setSpecularColor(color);

        Sphere sphere = new Sphere(5); // Rayon de 5
        sphere.setMaterial(col);

        // Positionner la sphère en fonction de la latitude et de la longitude
        sphere.setTranslateZ(-sph.getRadius()); // Position de base de la sphère (face avant de la Terre)

        // Rotation de la sphère sur l'axe Y en fonction de la longitude
        Rotate rotate = new Rotate(-a.getLongitude(), 0, 0, sph.getRadius(), Rotate.Y_AXIS);
        sphere.getTransforms().add(rotate);

        // Rotation de la sphère sur l'axe X en fonction de la latitude (ajustement de l'inclinaison)
        Rotate rotate2 = new Rotate(-a.getLatitude() * 2.0 / 3.0, -sphere.getTranslateX(),
                -sphere.getTranslateY(), -sphere.getTranslateZ(), Rotate.X_AXIS);
        sphere.getTransforms().add(rotate2);

        return sphere;
    }

    //Création des sphères jaunes pour les aéroports de départ des vols arrivant à un aéroport
    public void displayYellowSphere(ArrayList<Flight> flights) { // Liste des vols en paramètre
        for (Flight flight : flights) { // Boucle foreach
            if (flight.getDeparture() != null) { // Vérifie si le vol possède un aéroport de départ renseigné
                Sphere yellowSphere = createSphere(flight.getDeparture(), Color.YELLOW); // Création de la sphère jaune sur l'aéroport de départ du vol
                this.getChildren().add(yellowSphere); // Ajout de la sphère au Group JavaFX

                // Ancien code (conservé en commentaire)
                /*
                Sphere yS = createSphere(flight.getDeparture(), Color.YELLOW); // Création de la sphère jaune sur l'aéroport de départ du vol
                this.getChildren().add(yS); // Ajout de la sphère au Group JavaFX
                yellowSphere.add(yS); // Ajout de la sphère dans la liste yellowSphere
                */
            }
        }
    }

    // Afficher une sphère rouge sur un aéroport
    public void displayRedSphere(Aeroport a) {
        Sphere redSphere = createSphere(a, Color.RED); // Création de la sphère rouge
        this.getChildren().add(redSphere); // Ajout de la sphère rouge au Group JavaFX

        // Ancien code (conservé en commentaire)
        /*
        Sphere redSphere = createSphere(a, Color.RED);
        this.getChildren().add(redSphere); // Ajouter la sphère au groupe
        */
    }
}
