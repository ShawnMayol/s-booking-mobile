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

public class BusDetails extends AppCompatActivity {

    private TextView tvBusCompany, tvBusNumber, tvDepartureArrival, tvDuration, tvPrice;
    private Button btnConfirmBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bus_details);

        tvBusCompany = findViewById(R.id.tv_bus_company);
        tvBusNumber = findViewById(R.id.tv_bus_number);
        tvDepartureArrival = findViewById(R.id.tv_departure_arrival);
        tvDuration = findViewById(R.id.tv_duration);
        tvPrice = findViewById(R.id.tv_price);
        btnConfirmBooking = findViewById(R.id.btn_confirm_booking);

        // Get bus details from Intent
        String busCompany = getIntent().getStringExtra("busCompany");
        String busNumber = getIntent().getStringExtra("busNumber");
        String departureTime = getIntent().getStringExtra("departureTime");
        String arrivalTime = getIntent().getStringExtra("arrivalTime");
        String duration = getIntent().getStringExtra("duration");
        String price = getIntent().getStringExtra("price");

        // Set received data to views
        tvBusCompany.setText(busCompany);
        tvBusNumber.setText("Bus Number: " + busNumber);
        tvDepartureArrival.setText(departureTime + " → " + arrivalTime);
        tvDuration.setText("Duration: " + duration);
        tvPrice.setText(price);

        // Confirm Booking Button Click
        btnConfirmBooking.setOnClickListener(v -> {
            saveBooking(busCompany, busNumber, duration, departureTime, arrivalTime, price);
            Intent intent = new Intent(BusDetails.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void saveBooking(String busCompany, String busNumber, String duration, String departureTime, String arrivalTime, String price) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyTrips", Context.MODE_PRIVATE);
        Set<String> bookings = sharedPreferences.getStringSet("bus_bookings", new HashSet<>());

        // Store the bus trip details
        bookings.add(busCompany + " | Bus " + busNumber + " | " + duration + " | " + departureTime + " → " + arrivalTime + " | " + price);

        sharedPreferences.edit().putStringSet("bus_bookings", bookings).apply();
    }
}
