package uk.ac.abertay.cmp309.myyellowballv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/* This is the same adapter as in Lab6 but using my custom model AtpRankingsModel class */
public class AtpRankingsAdapter extends ArrayAdapter<AtpRankingsModel> {


    public AtpRankingsAdapter(Context context, ArrayList<AtpRankingsModel> data){
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* Get the player data for this position. */
        AtpRankingsModel player = getItem(position);
        /* Check if an existing view is being reused, otherwise inflate the view. */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rankings_layout, parent, false);
        }
        /* Lookup views. */
        TextView display_name = (TextView) convertView.findViewById(R.id.display_name);
        TextView display_country_code = (TextView) convertView.findViewById(R.id.display_country_code);
        TextView display_rank = (TextView) convertView.findViewById(R.id.display_rank);
        TextView display_points = (TextView) convertView.findViewById(R.id.display_points);

        /* Add the data to the template view. */
        display_name.setText(player.getName());
        display_country_code.setText(player.getCountry_code());
        display_rank.setText(Integer.toString(player.getRank()));
        display_points.setText(Integer.toString(player.getPoints()));
        /* Return the completed view to render on screen. */
        return convertView;
    }
}
