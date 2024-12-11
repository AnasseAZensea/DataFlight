public class Aeroport {
    private String name;
    private String IATA;
    private String country;
    private double latitude;
    private double longitude;

    // Constructeur
    public Aeroport(String name, String IATA, double latitude, double longitude, String country) {
        this.name = name;
        this.IATA = IATA;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }

    // Getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getIATA() {
        return IATA;
    }

    // Calcul de la distance avec un autre aéroport
    public double calculDistance(Aeroport a) {
        double thetaDiff = a.getLatitude() - this.latitude;
        double phiDiff = (a.getLongitude() - this.longitude) * Math.cos(Math.toRadians((this.latitude + a.getLatitude()) / 2));
        return Math.sqrt(thetaDiff * thetaDiff + phiDiff * phiDiff);
    }

    @Override
    public String toString() {
        return "Aeroport{" +
                "name='" + name + '\'' +
                ", IATA='" + IATA + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
