package ph.edu.usc.mayolshawn;

public class HotelItem {
    private String hotelName;
    private String price;
    private String roomType;

    public HotelItem(String hotelName, String price, String roomType) {
        this.hotelName = hotelName;
        this.price = price;
        this.roomType = roomType;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getPrice() {
        return price;
    }

    public String getRoomType() {
        return roomType;
    }
}
