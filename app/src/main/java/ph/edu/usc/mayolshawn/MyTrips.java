package ph.edu.usc.mayolshawn;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyTrips extends AppCompatActivity {

    private ListView myTripsListView;
    private Button btnCancelBooking;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> tripList;
    private int selectedPosition = -1; // No item selected by default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_trips);

        myTripsListView = findViewById(R.id.my_trips_list);
        btnCancelBooking = findViewById(R.id.btn_cancel_booking);

        // Fetch saved trips from SharedPreferences
        tripList = getSavedBookings();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, tripList);
        myTripsListView.setAdapter(adapter);
        myTripsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Handle item selection
        myTripsListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            btnCancelBooking.setVisibility(View.VISIBLE);
        });

        // Cancel booking button click
        btnCancelBooking.setOnClickListener(v -> {
            if (selectedPosition >= 0) {
                String removedTrip = tripList.remove(selectedPosition);
                adapter.notifyDataSetChanged();
                saveUpdatedBookings(tripList);
                Toast.makeText(this, "Cancelled: " + removedTrip, Toast.LENGTH_SHORT).show();
                btnCancelBooking.setVisibility(View.GONE);
            }
        });
    }

    private ArrayList<String> getSavedBookings() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyTrips", Context.MODE_PRIVATE);
        Set<String> allBookings = new HashSet<>();

        allBookings.addAll(sharedPreferences.getStringSet("flight_bookings", new HashSet<>()));
        allBookings.addAll(sharedPreferences.getStringSet("bus_bookings", new HashSet<>()));
        allBookings.addAll(sharedPreferences.getStringSet("train_bookings", new HashSet<>()));
        allBookings.addAll(sharedPreferences.getStringSet("hotel_bookings", new HashSet<>()));

        return new ArrayList<>(allBookings);
    }

    private void saveUpdatedBookings(ArrayList<String> updatedTrips) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyTrips", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> updatedSet = new HashSet<>(updatedTrips);
        editor.putStringSet("flight_bookings", updatedSet);
        editor.putStringSet("bus_bookings", updatedSet);
        editor.putStringSet("train_bookings", updatedSet);
        editor.putStringSet("hotel_bookings", updatedSet);

        editor.apply();
    }
}
