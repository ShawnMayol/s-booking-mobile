package ph.edu.usc.mayolshawn;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class HotelListAdapter extends ArrayAdapter<HotelItem> {
    private final Activity context;
    private final List<HotelItem> hotelList;

    public HotelListAdapter(Activity context, List<HotelItem> hotelList) {
        super(context, R.layout.hotel_list_item, hotelList);
        this.context = context;
        this.hotelList = hotelList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.hotel_list_item, null, true);

        TextView textHotelName = rowView.findViewById(R.id.tv_hotel_name);
        TextView textRoomType = rowView.findViewById(R.id.tv_room_type);
        TextView textPrice = rowView.findViewById(R.id.tv_price);

        HotelItem currentHotel = hotelList.get(position);

        textHotelName.setText(currentHotel.getHotelName());
        textRoomType.setText(currentHotel.getRoomType());
        textPrice.setText(currentHotel.getPrice());

        return rowView;
    }
}
