package ph.edu.usc.mayolshawn;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class BusListAdapter extends ArrayAdapter<BusItem> {
    private final Activity context;
    private final List<BusItem> busList;

    public BusListAdapter(Activity context, List<BusItem> busList) {
        super(context, R.layout.bus_list_item, busList);
        this.context = context;
        this.busList = busList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.bus_list_item, null, true);

        ImageView busIcon = rowView.findViewById(R.id.bus_icon);
        TextView textDepartureArrival = rowView.findViewById(R.id.tv_departure_arrival);
        TextView textBusInfo = rowView.findViewById(R.id.tv_bus_info);
        TextView textBusCompany = rowView.findViewById(R.id.tv_bus_company);
        TextView textPrice = rowView.findViewById(R.id.tv_price);

        BusItem currentBus = busList.get(position);

        textDepartureArrival.setText(currentBus.getDepartureTime() + " → " + currentBus.getArrivalTime());
        textBusInfo.setText("Bus " + currentBus.getBusNumber() + " • " + currentBus.getDuration());
        textBusCompany.setText(currentBus.getBusCompany());
        textPrice.setText(currentBus.getPrice());

        return rowView;
    }
}
