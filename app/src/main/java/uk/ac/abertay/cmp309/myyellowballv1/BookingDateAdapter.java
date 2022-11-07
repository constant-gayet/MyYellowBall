package uk.ac.abertay.cmp309.myyellowballv1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class BookingDateAdapter extends ArrayAdapter<LocalDate> {
    public BookingDateAdapter(Context context, ArrayList<LocalDate> data){
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* Get the player data for this position. */
        LocalDate date = getItem(position);
        /* Check if an existing view is being reused, otherwise inflate the view. */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.booking_layout, parent, false);
        }
        /* Lookup views. */
        Button btnDate = (Button) convertView.findViewById(R.id.btnDateLayout);
        GridView gridViewBookings = convertView.findViewById(R.id.gridViewBookings);

        //Set adapter of the child gridView for times buttons
        ArrayList<Integer> array = populateTimes(date.toString());
        BookingTimeAdapter gridAdapter = new BookingTimeAdapter(getContext(), array);
        gridViewBookings.setAdapter(gridAdapter);

        /* Add the data to the template view. */
        View finalConvertView = convertView;
        btnDate.setText(date.toString());
        btnDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ViewGroup.LayoutParams params = gridViewBookings.getLayoutParams();
                if (gridViewBookings.getVisibility()==View.VISIBLE){
                    gridViewBookings.setVisibility(View.INVISIBLE);
                    params.height -= 1000;
                    gridViewBookings.setLayoutParams(params);
                }
                else {
                    params.height += 1000;
                    gridViewBookings.setLayoutParams(params);
                    gridViewBookings.setVisibility(View.VISIBLE);
                }
            }
        });
        /* Return the completed view to render on screen. */
        return convertView;
    }

    protected ArrayList<Integer> populateTimes(String day){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int clubID = 1;
        int courtNumber = 1;
        ArrayList<Integer> timesList = new ArrayList<Integer>();
        int i = 0;
        while (i<25){
                timesList.add(i);
            i++;
        }
        return timesList;
    }
}

