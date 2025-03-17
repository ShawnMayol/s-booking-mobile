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

        // Predefined guest count
        String[] guestCounts = {"1 Guest", "2 Guests", "3 Guests", "4 Guests", "5+ Guests"};
        ArrayAdapter<String> guestAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, guestCounts);
        guestCountSpinner.setAdapter(guestAdapter);

        // Initialize hotel list
        hotelList = new ArrayList<>();
        adapter = new HotelListAdapter(this, hotelList);
        hotelListView.setAdapter(adapter);

        // Populate hotel data
        hotelLocations = new HashMap<>();
        populateHotelData();

        // Destination Change Listener
        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateHotelResults();
                bookHotelButton.setVisibility(View.GONE); // Hide button until selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // ListView Item Click Listener
        hotelListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedHotel = hotelList.get(position);
            bookHotelButton.setVisibility(View.VISIBLE); // Show book button when item is selected
        });

        // Book Hotel Button Click Listener
        bookHotelButton.setOnClickListener(v -> {
            if (selectedHotel != null) {
                Intent intent = new Intent(HotelSearch.this, HotelDetails.class);
                intent.putExtra("hotelName", selectedHotel.getHotelName());
                intent.putExtra("roomType", selectedHotel.getRoomType());
                intent.putExtra("price", selectedHotel.getPrice());
                intent.putExtra("checkinDate", "March 25, 2025");  // Placeholder
                intent.putExtra("checkoutDate", "March 30, 2025"); // Placeholder
                intent.putExtra("nights", "5"); // Placeholder
                startActivity(intent);
            }
        });
    }

    // Populates predefined hotel data for different locations
    private void populateHotelData() {
        hotelLocations.put("Manila", List.of(
                new HotelItem("Grand Manila Hotel", "₱3,500 per night", "Deluxe Room"),
                new HotelItem("Bayfront Hotel", "₱2,800 per night", "Standard Room"),
                new HotelItem("Luxury Suites", "₱5,000 per night", "Presidential Suite")
        ));

        hotelLocations.put("Cebu", List.of(
                new HotelItem("Cebu Seaview Resort", "₱4,200 per night", "Executive Suite"),
                new HotelItem("Mactan Beach Hotel", "₱3,000 per night", "Standard Room"),
                new HotelItem("Cebu City Grand", "₱3,500 per night", "Deluxe Suite")
        ));

        hotelLocations.put("Davao", List.of(
                new HotelItem("Davao Inn", "₱2,500 per night", "Budget Room"),
                new HotelItem("Pearl Farm Resort", "₱6,500 per night", "Luxury Villa"),
                new HotelItem("Eden Mountain Lodge", "₱3,800 per night", "Cabin Room")
        ));

        hotelLocations.put("Baguio", List.of(
                new HotelItem("Baguio Mountain Lodge", "₱3,200 per night", "Deluxe Room"),
                new HotelItem("Session Road Inn", "₱2,700 per night", "Budget Room"),
                new HotelItem("Baguio Grand Hotel", "₱4,000 per night", "Suite")
        ));

        hotelLocations.put("Boracay", List.of(
                new HotelItem("Boracay Beach Resort", "₱5,500 per night", "Ocean View Suite"),
                new HotelItem("White Sands Hotel", "₱4,000 per night", "Standard Room"),
                new HotelItem("Boracay Island Villas", "₱6,200 per night", "Luxury Villa")
        ));

        hotelLocations.put("Palawan", List.of(
                new HotelItem("El Nido Resorts", "₱8,500 per night", "Luxury Suite"),
                new HotelItem("Coron Seaside Inn", "₱3,200 per night", "Seaview Room"),
                new HotelItem("Puerto Princesa Hotel", "₱3,800 per night", "Deluxe Room")
        ));
    }

    // Updates the hotel list based on selected destination
    private void updateHotelResults() {
        String selectedDestination = destinationSpinner.getSelectedItem().toString();

        hotelList.clear();
        if (hotelLocations.containsKey(selectedDestination)) {
            hotelList.addAll(hotelLocations.get(selectedDestination));
        } else {
            hotelList.add(new HotelItem("No Hotels Available", "", ""));
        }

        adapter.notifyDataSetChanged();
    }
}
