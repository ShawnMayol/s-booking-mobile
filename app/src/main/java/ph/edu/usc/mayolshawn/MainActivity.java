package ph.edu.usc.mayolshawn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Flight Search
        ImageView airplaneImage = findViewById(R.id.airplane);
        airplaneImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FlightSearch.class);
            startActivity(intent);
        });

        // Bus Search
        ImageView busImage = findViewById(R.id.bus);
        busImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BusSearch.class);
            startActivity(intent);
        });

        // Train Search
        ImageView trainImage = findViewById(R.id.train);
        trainImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TrainSearch.class);
            startActivity(intent);
        });

        // Hotel Search
        ImageView hotelImage = findViewById(R.id.hotel);
        hotelImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HotelSearch.class);
            startActivity(intent);
        });

        // My Trips
        ImageView myTripsImage = findViewById(R.id.mytrips);
        myTripsImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyTrips.class);
            startActivity(intent);
        });
    }
}
