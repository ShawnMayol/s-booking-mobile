package ph.edu.usc.mayolshawn;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class TrainListAdapter extends ArrayAdapter<TrainItem> {
    private final Activity context;
    private final List<TrainItem> trainList;

    public TrainListAdapter(Activity context, List<TrainItem> trainList) {
        super(context, R.layout.train_list_item, trainList);
        this.context = context;
        this.trainList = trainList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.train_list_item, null, true);

        ImageView trainIcon = rowView.findViewById(R.id.train_icon);
        TextView textDepartureArrival = rowView.findViewById(R.id.tv_departure_arrival);
        TextView textTrainInfo = rowView.findViewById(R.id.tv_train_info);
        TextView textTrainCompany = rowView.findViewById(R.id.tv_train_company);
        TextView textPrice = rowView.findViewById(R.id.tv_price);

        TrainItem currentTrain = trainList.get(position);

        textDepartureArrival.setText(currentTrain.getDepartureTime() + " → " + currentTrain.getArrivalTime());
        textTrainInfo.setText("Train " + currentTrain.getTrainNumber() + " • " + currentTrain.getDuration());
        textTrainCompany.setText(currentTrain.getTrainCompany());
        textPrice.setText(currentTrain.getPrice());

        return rowView;
    }
}
