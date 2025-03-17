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

public class BusSearch extends AppCompatActivity {

    private Spinner fromCitySpinner, toCitySpinner;
    private ListView busListView;
    private Button bookBusButton;
    private BusListAdapter adapter;
    private ArrayList<BusItem> busList;
    private Map<String, List<BusItem>> busRoutes;
    private BusItem selectedBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bus_search);

        fromCitySpinner = findViewById(R.id.from_city);
        toCitySpinner = findViewById(R.id.to_city);
        busListView = findViewById(R.id.bus_list);
        bookBusButton = findViewById(R.id.book_bus);

        bookBusButton.setVisibility(View.GONE); // Hide button initially

        // Predefined locations
        String[] locations = {
                "Manila", "Cebu", "Davao",
                "Baguio", "Iloilo", "Bacolod"
        };

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, locations);
        fromCitySpinner.setAdapter(locationAdapter);
        toCitySpinner.setAdapter(locationAdapter);

        // Initialize ListView
        busList = new ArrayList<>();
        adapter = new BusListAdapter(this, busList);
        busListView.setAdapter(adapter);

        // Initialize bus routes mapping
        busRoutes = new HashMap<>();
        populateBusRoutes();

        // Dropdown Change Listener
        AdapterView.OnItemSelectedListener locationChangeListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateBusResults();
                bookBusButton.setVisibility(View.GONE); // Hide book button when new search is made
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        fromCitySpinner.setOnItemSelectedListener(locationChangeListener);
        toCitySpinner.setOnItemSelectedListener(locationChangeListener);

        // ListView Item Click Listener
        busListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedBus = busList.get(position);
            bookBusButton.setVisibility(View.VISIBLE); // Show book button when item is selected
        });

        // Book Bus Button Click Listener
        bookBusButton.setOnClickListener(v -> {
            if (selectedBus != null) {
                Intent intent = new Intent(BusSearch.this, BusDetails.class);
                intent.putExtra("busCompany", selectedBus.getBusCompany());
                intent.putExtra("busNumber", selectedBus.getBusNumber());
                intent.putExtra("departureTime", selectedBus.getDepartureTime());
                intent.putExtra("arrivalTime", selectedBus.getArrivalTime());
                intent.putExtra("duration", selectedBus.getDuration());
                intent.putExtra("price", selectedBus.getPrice());
                startActivity(intent);
            }
        });
    }

    private void populateBusRoutes() {
        // Map bus routes to specific bus data
        busRoutes.put("Manila-Cebu", List.of(
                new BusItem("Philtranco", "BUS123", "8h 30m", "07:00 AM", "3:30 PM", "₱950"),
                new BusItem("Ceres Liner", "BUS456", "9h 00m", "09:30 AM", "6:30 PM", "₱980")
        ));

        busRoutes.put("Cebu-Davao", List.of(
                new BusItem("Davao Metro Shuttle", "BUS789", "12h 15m", "06:00 AM", "6:15 PM", "₱1200"),
                new BusItem("Bachelor Express", "BUS101", "11h 45m", "08:15 AM", "8:00 PM", "₱1250")
        ));

        busRoutes.put("Baguio-Manila", List.of(
                new BusItem("Victory Liner", "BUS303", "5h 45m", "06:30 AM", "12:15 PM", "₱700"),
                new BusItem("Genesis Transport", "BUS404", "6h 00m", "07:00 AM", "1:00 PM", "₱720")
        ));

        busRoutes.put("Iloilo-Bacolod", List.of(
                new BusItem("Ceres Liner", "BUS505", "2h 30m", "09:00 AM", "11:30 AM", "₱350"),
                new BusItem("Supreme Bus", "BUS606", "2h 45m", "10:15 AM", "1:00 PM", "₱370")
        ));
    }

    private void updateBusResults() {
        String fromCity = fromCitySpinner.getSelectedItem().toString();
        String toCity = toCitySpinner.getSelectedItem().toString();
        String routeKey = fromCity + "-" + toCity;

        busList.clear();

        if (fromCity.equals(toCity)) {
            busList.add(new BusItem("No Buses Available", "", "", "", "", ""));
        } else if (busRoutes.containsKey(routeKey)) {
            busList.addAll(busRoutes.get(routeKey));
        } else {
            busList.add(new BusItem("No Scheduled Buses", "", "", "", "", ""));
        }

        adapter.notifyDataSetChanged();
    }
}
