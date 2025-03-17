package ph.edu.usc.mayolshawn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;

public class HotelDetails extends AppCompatActivity {

    private TextView tvHotelName, tvRoomType, tvCheckinCheckout, tvNights, tvPrice;
    private Button btnConfirmBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hotel_details);

        tvHotelName = findViewById(R.id.tv_hotel_name);
        tvRoomType = findViewById(R.id.tv_room_type);
        tvCheckinCheckout = findViewById(R.id.tv_checkin_checkout);
        tvNights = findViewById(R.id.tv_nights);
        tvPrice = findViewById(R.id.tv_price);
        btnConfirmBooking = findViewById(R.id.btn_confirm_booking);

        // Get hotel details from Intent
        String hotelName = getIntent().getStringExtra("hotelName");
        String roomType = getIntent().getStringExtra("roomType");
        String checkinDate = getIntent().getStringExtra("checkinDate");
        String checkoutDate = getIntent().getStringExtra("checkoutDate");
        String nights = getIntent().getStringExtra("nights");
        String price = getIntent().getStringExtra("price");

        // Set received data to views
        tvHotelName.setText(hotelName);
        tvRoomType.setText("Room Type: " + roomType);
        tvCheckinCheckout.setText("Check-in: " + checkinDate + "  |  Check-out: " + checkoutDate);
        tvNights.setText("Number of Nights: " + nights);
        tvPrice.setText(price);

        // Confirm Booking Button Click
        btnConfirmBooking.setOnClickListener(v -> {
            saveBooking(hotelName, roomType, checkinDate, checkoutDate, nights, price);
            Intent intent = new Intent(HotelDetails.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void saveBooking(String hotelName, String roomType, String checkinDate, String checkoutDate, String nights, String price) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyTrips", Context.MODE_PRIVATE);
        Set<String> bookings = sharedPreferences.getStringSet("hotel_bookings", new HashSet<>());

        // Store the hotel booking details
        bookings.add(hotelName + " | " + roomType + " | " + checkinDate + " - " + checkoutDate + " | " + nights + " nights | " + price);

        sharedPreferences.edit().putStringSet("hotel_bookings", bookings).apply();
    }
}
