package ph.edu.usc.mayolshawn;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FlightListAdapter extends ArrayAdapter<FlightItem> {
    private final Activity context;
    private final List<FlightItem> flights;

    public FlightListAdapter(Activity context, List<FlightItem> flights) {
        super(context, R.layout.flight_list_item, flights);
        this.context = context;
        this.flights = flights;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.flight_list_item, null, true);

        // Get views
        TextView tvAirline = rowView.findViewById(R.id.tv_airline_name);
        TextView tvFlightInfo = rowView.findViewById(R.id.tv_flight_info);
        TextView tvDepartureArrival = rowView.findViewById(R.id.tv_departure_arrival);
        TextView tvPrice = rowView.findViewById(R.id.tv_price);

        // Set values
        FlightItem flight = flights.get(position);
        tvAirline.setText(flight.getAirlineName());
        tvFlightInfo.setText(flight.getFlightNumber() + " • " + flight.getDuration());
        tvDepartureArrival.setText(flight.getDepartureTime() + " → " + flight.getArrivalTime());
        tvPrice.setText(flight.getPrice());

        return rowView;
    }
}
