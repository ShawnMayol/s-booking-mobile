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

public class FlightSearch extends AppCompatActivity {

    private Spinner fromCitySpinner, toCitySpinner;
    private ListView flightListView;
    private FlightListAdapter adapter;
    private ArrayList<FlightItem> flightList;
    private Map<String, List<FlightItem>> flightRoutes;
    private Button bookFlightButton;
    private FlightItem selectedFlight; // Holds the selected flight

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flight_search);

        fromCitySpinner = findViewById(R.id.from_city);
        toCitySpinner = findViewById(R.id.to_city);
        flightListView = findViewById(R.id.flight_list);
        bookFlightButton = findViewById(R.id.book_flight);

        bookFlightButton.setVisibility(View.GONE); // Hide button initially

        // Predefined locations
        String[] locations = {
                "Japan", "Korea", "Philippines",
                "France", "Germany", "Italy",
                "USA", "Canada", "Mexico"
        };

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, locations);
        fromCitySpinner.setAdapter(locationAdapter);
        toCitySpinner.setAdapter(locationAdapter);

        // Initialize ListView
        flightList = new ArrayList<>();
        adapter = new FlightListAdapter(this, flightList);
        flightListView.setAdapter(adapter);

        // Initialize flight routes mapping
        flightRoutes = new HashMap<>();
        populateFlightRoutes();

        // Dropdown Change Listener
        AdapterView.OnItemSelectedListener locationChangeListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateFlightResults();
                bookFlightButton.setVisibility(View.GONE); // Hide book button when new search is made
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        fromCitySpinner.setOnItemSelectedListener(locationChangeListener);
        toCitySpinner.setOnItemSelectedListener(locationChangeListener);

        // ListView Item Click Listener
        flightListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedFlight = flightList.get(position);
            bookFlightButton.setVisibility(View.VISIBLE); // Show the book button when an item is selected
        });

        // Book Flight Button Click Listener
        bookFlightButton.setOnClickListener(v -> {
            if (selectedFlight != null) {
                Intent intent = new Intent(FlightSearch.this, FlightDetails.class);
                intent.putExtra("airlineName", selectedFlight.getAirlineName());
                intent.putExtra("flightNumber", selectedFlight.getFlightNumber());
                intent.putExtra("duration", selectedFlight.getDuration());
                intent.putExtra("departureTime", selectedFlight.getDepartureTime());
                intent.putExtra("arrivalTime", selectedFlight.getArrivalTime());
                intent.putExtra("price", selectedFlight.getPrice());
                startActivity(intent);
            }
        });
    }

    private void populateFlightRoutes() {
        // Map flight routes to specific flight data
        flightRoutes.put("Japan-Korea", List.of(
                new FlightItem("Japan Airlines", "JAL123", "2h 00m", "08:00 AM", "10:00 AM", "$150"),
                new FlightItem("Korean Air", "KE456", "2h 15m", "09:30 AM", "11:45 AM", "$160")
        ));

        flightRoutes.put("Japan-Philippines", List.of(
                new FlightItem("Japan Airlines", "JAL789", "4h 30m", "07:00 AM", "11:30 AM", "$220"),
                new FlightItem("Philippine Airlines", "PAL101", "4h 45m", "08:15 AM", "1:00 PM", "$210")
        ));

        flightRoutes.put("USA-Germany", List.of(
                new FlightItem("Lufthansa", "LH303", "10h 00m", "06:00 AM", "4:00 PM", "$800"),
                new FlightItem("American Airlines", "AA505", "10h 15m", "07:30 AM", "5:45 PM", "$820")
        ));

        flightRoutes.put("France-Italy", List.of(
                new FlightItem("Air France", "AF606", "1h 30m", "12:00 PM", "1:30 PM", "$120"),
                new FlightItem("Alitalia", "AZ707", "1h 45m", "03:00 PM", "4:45 PM", "$130")
        ));

        flightRoutes.put("Canada-Mexico", List.of(
                new FlightItem("Air Canada", "AC808", "6h 30m", "09:00 AM", "3:30 PM", "$450"),
                new FlightItem("Aeroméxico", "AM909", "6h 15m", "11:15 AM", "5:30 PM", "$460")
        ));
    }

    private void updateFlightResults() {
        String fromCity = fromCitySpinner.getSelectedItem().toString();
        String toCity = toCitySpinner.getSelectedItem().toString();
        String routeKey = fromCity + "-" + toCity;

        flightList.clear();

        if (fromCity.equals(toCity)) {
            flightList.add(new FlightItem("No Flights Available", "", "", "", "", ""));
        } else if (flightRoutes.containsKey(routeKey)) {
            flightList.addAll(flightRoutes.get(routeKey));
        } else {
            flightList.add(new FlightItem("No Scheduled Flights", "", "", "", "", ""));
        }

        adapter.notifyDataSetChanged();
    }
}
