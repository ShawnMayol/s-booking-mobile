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

public class FlightDetails extends AppCompatActivity {

    private TextView tvAirline, tvFlightNumber, tvDuration, tvDeparture, tvArrival, tvPrice;
    private Button btnConfirmBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flight_details);

        tvAirline = findViewById(R.id.tv_airline_name);
        tvFlightNumber = findViewById(R.id.tv_flight_number);
        tvDuration = findViewById(R.id.tv_duration);
        tvDeparture = findViewById(R.id.tv_departure_code);
        tvArrival = findViewById(R.id.tv_arrival_code);
        tvPrice = findViewById(R.id.tv_price);
        btnConfirmBooking = findViewById(R.id.btn_confirm_booking);

        // Get flight details from Intent
        String airline = getIntent().getStringExtra("airlineName");
        String flightNumber = getIntent().getStringExtra("flightNumber");
        String duration = getIntent().getStringExtra("duration");
        String departureTime = getIntent().getStringExtra("departureTime");
        String arrivalTime = getIntent().getStringExtra("arrivalTime");
        String price = getIntent().getStringExtra("price");

        tvAirline.setText(airline);
        tvFlightNumber.setText("Flight: " + flightNumber);
        tvDuration.setText(duration);
        tvDeparture.setText(departureTime);
        tvArrival.setText(arrivalTime);
        tvPrice.setText(price);

        // Confirm Booking Button Click
        btnConfirmBooking.setOnClickListener(v -> {
            saveBooking(airline, flightNumber, duration, departureTime, arrivalTime, price);
            Intent intent = new Intent(FlightDetails.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void saveBooking(String airline, String flightNumber, String duration, String departureTime, String arrivalTime, String price) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyTrips", Context.MODE_PRIVATE);
        Set<String> bookings = sharedPreferences.getStringSet("bookings", new HashSet<>());

        bookings.add(airline + " | " + flightNumber + " | " + duration + " | " + departureTime + " → " + arrivalTime + " | " + price);

        sharedPreferences.edit().putStringSet("bookings", bookings).apply();
    }
}
