import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonFlightFiller {
    private ArrayList<Flight> list = new ArrayList<>(); // Liste de vols (Flight)

    // Constructeur de la classe JsonFlightFiller
    public JsonFlightFiller(String jsonString) {
        try {
            // Convertir la chaîne JSON en flux
            InputStream is = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            JsonReader rdr = Json.createReader(is);
            JsonObject obj = rdr.readObject();
            JsonArray results = obj.getJsonArray("data");

            // Parcourir chaque vol dans le tableau "data"
            for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                try {
                    // Extraction des informations de vol
                    String flightDate = result.getString("flight_date");
                    String flightStatus = result.getString("flight_status");

                    JsonObject departure = result.getJsonObject("departure");
                    String departureAirport = departure.getString("airport");

                    JsonObject arrival = result.getJsonObject("arrival");
                    String arrivalAirport = arrival.getString("airport");

                    JsonObject airline = result.getJsonObject("airline");
                    String airlineName = airline.getString("name");

                    JsonObject flight = result.getJsonObject("flight");
                    String flightNumber = flight.getString("number");

                    JsonObject aircraft = result.getJsonObject("aircraft");
                    String aircraftModel = aircraft.getString("iata");

                    // Informations live (latitude, longitude, altitude)
                    HashMap<String, Object> liveData = new HashMap<>();
                    if (result.containsKey("live")) {
                        JsonObject live = result.getJsonObject("live");
                        liveData.put("latitude", live.getJsonNumber("latitude").doubleValue());
                        liveData.put("longitude", live.getJsonNumber("longitude").doubleValue());
                        liveData.put("altitude", live.getInt("altitude"));
                    }

                    // Créer l'objet Flight
                    Flight flightObj = new Flight(
                            flightDate, flightStatus, departureAirport,
                            arrivalAirport, airlineName, flightNumber,
                            aircraftModel, liveData
                    );

                    list.add(flightObj);
                } catch (Exception e) {
                    System.out.println("Erreur de traitement d'un vol : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de la réponse JSON : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir la liste des vols
    public ArrayList<Flight> getList() {
        return list;
    }

    // Afficher la liste de tous les vols
    public void displayFlights() {
        for (Flight flight : list) {
            System.out.println(flight);
        }
    }
}
