import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

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

    // Créer une sphère rouge sur un aéroport donné
    public Sphere createSphere(Aeroport a, Color color) {
        PhongMaterial col = new PhongMaterial();
        col.setDiffuseColor(color);
        col.setSpecularColor(color);

        Sphere sphere = new Sphere(5); // Rayon de 5
        sphere.setMaterial(col);

        // Positionner la sphère en fonction de la latitude et de la longitude
        /*double x = -sph.getRadius() * Math.cos(Math.toRadians(a.getLatitude())) * Math.sin(Math.toRadians(a.getLongitude()));
        double y = -sph.getRadius() * Math.sin(Math.toRadians(a.getLatitude()));
        double z = sph.getRadius() * Math.cos(Math.toRadians(a.getLatitude())) * Math.cos(Math.toRadians(a.getLongitude()));

        sphere.setTranslateX(x);
        sphere.setTranslateY(y);
        sphere.setTranslateZ(z);
*/
        sphere.setTranslateZ(-sph.getRadius());
      //  Rotate rotate = new Rotate(45, -sphere.getTranslateX(), -sphere.getTranslateY(), -sphere.getTranslateZ(),Rotate.Y_AXIS);
        Rotate rotate = new Rotate(-a.getLongitude(), 0, 0, 300,Rotate.Y_AXIS);
        sphere.getTransforms().add(rotate);
        Rotate rotate2 = new Rotate(-a.getLatitude()*2.0/3.0, -sphere.getTranslateX(),
                -sphere.getTranslateY(),-sphere.getTranslateZ(),Rotate.X_AXIS);
        sphere.getTransforms().add(rotate2);
        return sphere;
    }

    // Afficher une sphère rouge sur un aéroport
    public void displayRedSphere(Aeroport a) {
        Sphere redSphere = createSphere(a, Color.RED);
        this.getChildren().add(redSphere); // Ajouter la sphère au groupe
    }
}
