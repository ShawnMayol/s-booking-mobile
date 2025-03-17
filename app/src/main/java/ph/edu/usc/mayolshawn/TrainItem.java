package ph.edu.usc.mayolshawn;

public class TrainItem {
    private String trainCompany;
    private String trainNumber;
    private String duration;
    private String departureTime;
    private String arrivalTime;
    private String price;

    public TrainItem(String trainCompany, String trainNumber, String duration, String departureTime, String arrivalTime, String price) {
        this.trainCompany = trainCompany;
        this.trainNumber = trainNumber;
        this.duration = duration;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    public String getTrainCompany() {
        return trainCompany;
    }

    public String getTrainNumber() {
        return trainNumber;
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
