package ph.edu.usc.mayolshawn;

public class BusItem {
    private String busCompany;
    private String busNumber;
    private String duration;
    private String departureTime;
    private String arrivalTime;
    private String price;

    public BusItem(String busCompany, String busNumber, String duration, String departureTime, String arrivalTime, String price) {
        this.busCompany = busCompany;
        this.busNumber = busNumber;
        this.duration = duration;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    public String getBusCompany() {
        return busCompany;
    }

    public String getBusNumber() {
        return busNumber;
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
