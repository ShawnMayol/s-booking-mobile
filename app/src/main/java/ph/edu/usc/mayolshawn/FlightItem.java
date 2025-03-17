package ph.edu.usc.mayolshawn;

public class FlightItem {
    private String airlineName;
    private String flightNumber;
    private String duration;
    private String departureTime;
    private String arrivalTime;
    private String price;

    public FlightItem(String airlineName, String flightNumber, String duration, String departureTime, String arrivalTime, String price) {
        this.airlineName = airlineName;
        this.flightNumber = flightNumber;
        this.duration = duration;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDuration() {
        return duration;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getPrice() {
        return price;
    }
}
