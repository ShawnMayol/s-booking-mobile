package ph.edu.usc.mayolshawn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelSearch extends AppCompatActivity {

    private Spinner destinationSpinner, guestCountSpinner;
    private ListView hotelListView;
    private Button bookHotelButton;
    private HotelListAdapter adapter;
    private ArrayList<HotelItem> hotelList;
    private Map<String, List<HotelItem>> hotelLocations;
    private HotelItem selectedHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hotel_search);

        destinationSpinner = findViewById(R.id.destination_city);
        guestCountSpinner = findViewById(R.id.guest_count);
        hotelListView = findViewById(R.id.hotel_list);
        bookHotelButton = findViewById(R.id.book_hotel);

        bookHotelButton.setVisibility(View.GONE); // Hide button initially

        // Predefined locations
        String[] locations = {"Manila", "Cebu", "Davao", "Baguio", "Boracay", "Palawan"};
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        destinationSpinner.setAdapter(locationAdapter);

        // Initialize hotel list
        hotelList = new ArrayList<>();
        adapter = new HotelListAdapter(this, hotelList);
        hotelListView.setAdapter(adapter);

        // Populate hotel data
        hotelLocations = new HashMap<>();
        populateHotelData();
    }

    private void populateHotelData() {
        hotelLocations.put("Manila", List.of(
                new HotelItem("Grand Manila Hotel", "₱3,500 per night", "Deluxe Room"),
                new HotelItem("Bayfront Hotel", "₱2,800 per night", "Standard Room"),
                new HotelItem("Luxury Suites", "₱5,000 per night", "Presidential Suite")
        ));
    }

}
