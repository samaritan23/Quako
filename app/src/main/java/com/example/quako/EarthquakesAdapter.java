package com.example.quako;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class EarthquakesAdapter extends ArrayAdapter<Earthquakes> {


    public EarthquakesAdapter(Activity context, ArrayList<Earthquakes> earthquakes){
        super(context, 0, earthquakes);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        Earthquakes current_earthquake = getItem(position);

        TextView place = listItemView.findViewById(R.id.place_view);
        place.setText(current_earthquake.getPlace());

        TextView offset = listItemView.findViewById(R.id.offset_view);
        offset.setText(current_earthquake.getOffset());

        TextView date = listItemView.findViewById(R.id.date_view);
        date.setText(formatDate(current_earthquake.getDate()));

        TextView time = listItemView.findViewById(R.id.time_view);
        time.setText(formatTime(current_earthquake.getDate()));

        TextView magnitude = listItemView.findViewById(R.id.magnitude_view);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(current_earthquake.getMagnitude());

        magnitudeCircle.setColor(magnitudeColor);
        magnitude.setText((formatMagnitude(current_earthquake.getMagnitude())));

        return listItemView;

    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1: magnitudeColorResourceId = R.color.magnitude1; break;
            case 2: magnitudeColorResourceId = R.color.magnitude2; break;
            case 3: magnitudeColorResourceId = R.color.magnitude3; break;
            case 4: magnitudeColorResourceId = R.color.magnitude4; break;
            case 5: magnitudeColorResourceId = R.color.magnitude5; break;
            case 6: magnitudeColorResourceId = R.color.magnitude6; break;
            case 7: magnitudeColorResourceId = R.color.magnitude7; break;
            case 8: magnitudeColorResourceId = R.color.magnitude8; break;
            case 9: magnitudeColorResourceId = R.color.magnitude9; break;
            default: magnitudeColorResourceId = R.color.magnitude10plus; break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private String formatMagnitude(double magnitude) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(magnitude);
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}