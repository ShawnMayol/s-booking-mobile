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

public class TrainDetails extends AppCompatActivity {

    private TextView tvTrainCompany, tvTrainNumber, tvDepartureArrival, tvDuration, tvPrice;
    private Button btnConfirmBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_train_details);

        tvTrainCompany = findViewById(R.id.tv_train_company);
        tvTrainNumber = findViewById(R.id.tv_train_number);
        tvDepartureArrival = findViewById(R.id.tv_departure_arrival);
        tvDuration = findViewById(R.id.tv_duration);
        tvPrice = findViewById(R.id.tv_price);
        btnConfirmBooking = findViewById(R.id.btn_confirm_booking);

        // Get train details from Intent
        String trainCompany = getIntent().getStringExtra("trainCompany");
        String trainNumber = getIntent().getStringExtra("trainNumber");
        String departureTime = getIntent().getStringExtra("departureTime");
        String arrivalTime = getIntent().getStringExtra("arrivalTime");
        String duration = getIntent().getStringExtra("duration");
        String price = getIntent().getStringExtra("price");

        // Set received data to views
        tvTrainCompany.setText(trainCompany);
        tvTrainNumber.setText("Train Number: " + trainNumber);
        tvDepartureArrival.setText(departureTime + " → " + arrivalTime);
        tvDuration.setText("Duration: " + duration);
        tvPrice.setText(price);

        // Confirm Booking Button Click
        btnConfirmBooking.setOnClickListener(v -> {
            saveBooking(trainCompany, trainNumber, duration, departureTime, arrivalTime, price);
            Intent intent = new Intent(TrainDetails.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void saveBooking(String trainCompany, String trainNumber, String duration, String departureTime, String arrivalTime, String price) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyTrips", Context.MODE_PRIVATE);
        Set<String> bookings = sharedPreferences.getStringSet("train_bookings", new HashSet<>());

        // Store the train trip details
        bookings.add(trainCompany + " | Train " + trainNumber + " | " + duration + " | " + departureTime + " → " + arrivalTime + " | " + price);

        sharedPreferences.edit().putStringSet("train_bookings", bookings).apply();
    }
}
