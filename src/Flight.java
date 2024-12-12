import java.util.HashMap;

public class Flight {
    private String flightDate;
    private String flightStatus;
    private String departureAirport;
    private String arrivalAirport;
    private String airline;
    private String flightNumber;
    private String aircraft;
    private HashMap<String, Object> liveData; // Données live (latitude, longitude, altitude, etc.)

    // Constructeur complet
    public Flight(String flightDate, String flightStatus, String departureAirport,
                  String arrivalAirport, String airline, String flightNumber,
                  String aircraft, HashMap<String, Object> liveData) {
        this.flightDate = flightDate;
        this.flightStatus = flightStatus;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.aircraft = aircraft;
        this.liveData = liveData;
    }

    // Getters
    public String getFlightDate() {
        return flightDate;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getAirline() {
        return airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAircraft() {
        return aircraft;
    }

    public HashMap<String, Object> getLiveData() {
        return liveData;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightDate='" + flightDate + '\'' +
                ", flightStatus='" + flightStatus + '\'' +
                ", departureAirport='" + departureAirport + '\'' +
                ", arrivalAirport='" + arrivalAirport + '\'' +
                ", airline='" + airline + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", aircraft='" + aircraft + '\'' +
                ", liveData=" + liveData +
                '}';
    }
}
