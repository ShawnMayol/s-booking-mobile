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

public class TrainSearch extends AppCompatActivity {

    private Spinner fromCitySpinner, toCitySpinner;
    private ListView trainListView;
    private Button bookTrainButton;
    private TrainListAdapter adapter;
    private ArrayList<TrainItem> trainList;
    private Map<String, List<TrainItem>> trainRoutes;
    private TrainItem selectedTrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_train_search);

        fromCitySpinner = findViewById(R.id.from_city);
        toCitySpinner = findViewById(R.id.to_city);
        trainListView = findViewById(R.id.train_list);
        bookTrainButton = findViewById(R.id.book_train);

        bookTrainButton.setVisibility(View.GONE); // Hide button initially

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
        trainList = new ArrayList<>();
        adapter = new TrainListAdapter(this, trainList);
        trainListView.setAdapter(adapter);

        // Initialize train routes mapping
        trainRoutes = new HashMap<>();
        populateTrainRoutes();

        // Dropdown Change Listener
        AdapterView.OnItemSelectedListener locationChangeListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTrainResults();
                bookTrainButton.setVisibility(View.GONE); // Hide book button when new search is made
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        fromCitySpinner.setOnItemSelectedListener(locationChangeListener);
        toCitySpinner.setOnItemSelectedListener(locationChangeListener);

        // ListView Item Click Listener
        trainListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedTrain = trainList.get(position);
            bookTrainButton.setVisibility(View.VISIBLE); // Show book button when item is selected
        });

        // Book Train Button Click Listener
        bookTrainButton.setOnClickListener(v -> {
            if (selectedTrain != null) {
                Intent intent = new Intent(TrainSearch.this, TrainDetails.class);
                intent.putExtra("trainCompany", selectedTrain.getTrainCompany());
                intent.putExtra("trainNumber", selectedTrain.getTrainNumber());
                intent.putExtra("departureTime", selectedTrain.getDepartureTime());
                intent.putExtra("arrivalTime", selectedTrain.getArrivalTime());
                intent.putExtra("duration", selectedTrain.getDuration());
                intent.putExtra("price", selectedTrain.getPrice());
                startActivity(intent);
            }
        });
    }

    private void populateTrainRoutes() {
        trainRoutes.put("Manila-Cebu", List.of(
                new TrainItem("Philippine Railways", "PR101", "6h 30m", "06:00 AM", "12:30 PM", "₱850"),
                new TrainItem("National Express", "NE202", "7h 00m", "07:30 AM", "2:30 PM", "₱900")
        ));

        trainRoutes.put("Manila-Davao", List.of(
                new TrainItem("Davao Express", "DE303", "10h 45m", "05:30 AM", "4:15 PM", "₱1,200"),
                new TrainItem("Mindanao Rail", "MR404", "11h 15m", "06:45 AM", "6:00 PM", "₱1,250")
        ));

        trainRoutes.put("Cebu-Baguio", List.of(
                new TrainItem("North Luzon Express", "NLE505", "8h 15m", "07:00 AM", "3:15 PM", "₱950"),
                new TrainItem("Cebu Pacific Rail", "CP606", "8h 45m", "08:00 AM", "4:45 PM", "₱1,000")
        ));

        trainRoutes.put("Iloilo-Bacolod", List.of(
                new TrainItem("Visayas Rail", "VR707", "2h 45m", "09:00 AM", "11:45 AM", "₱600"),
                new TrainItem("Western Express", "WE808", "3h 00m", "10:30 AM", "1:30 PM", "₱650")
        ));
    }
    private void updateTrainResults() {
        String fromCity = fromCitySpinner.getSelectedItem().toString();
        String toCity = toCitySpinner.getSelectedItem().toString();
        String routeKey = fromCity + "-" + toCity;

        trainList.clear();

        if (fromCity.equals(toCity)) {
            trainList.add(new TrainItem("No Trains Available", "", "", "", "", ""));
        } else if (trainRoutes.containsKey(routeKey)) {
            trainList.addAll(trainRoutes.get(routeKey));
        } else {
            trainList.add(new TrainItem("No Scheduled Trains", "", "", "", "", ""));
        }

        adapter.notifyDataSetChanged();
    }
}
