package uk.ac.abertay.cmp309.myyellowballv1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookingTimeAdapter extends ArrayAdapter<Integer> {
    private FirebaseAuth auth;
    private BookingDataService dataManager;

    public BookingTimeAdapter(Context context, ArrayList<Integer> data) {
        super(context, 0, data);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        /* Get the player data for this position. */
        Integer time = getItem(position);
        /* Check if an existing view is being reused, otherwise inflate the view. */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.booking_date_layout, parent, false);
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        /* Lookup views. */
        Button btnTime = (Button) convertView.findViewById(R.id.btnTimeLayout);
        dataManager = new BookingDataService(getContext());
        /* Add the data to the template view. */
        View finalConvertView = convertView;
        btnTime.setText(time.toString()+":00");
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Button btnClicked = (Button) v;
                //Recovering which date the timeButton clicked belongs to
                int index = getParentIndex(btnClicked);
                LocalDate today = LocalDate.now( ZoneId.of( "Europe/London" ) ) ;
                LocalDate selectedDate = today.plusDays(index);
                //Recovering the name of the connected player
                db.collection("Users")
                        .whereEqualTo("email", user.getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String name = document.getString("firstName") + document.getString("lastName");
                                    }
                                } else {
                                    String name = "Unknown";
                                }
                            }
                        });

                //Creating the booking document according to the isAvailable boolean
                db.collection("Clubs")
                        .document(String.valueOf(1))
                        .collection("courts")
                        .document("court_"+String.valueOf(1))
                        .collection("bookings")
                        .document(selectedDate.toString())
                        .collection("times")
                        .document(String.valueOf(position))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    Boolean isAvailable;
                                    isAvailable = document.getBoolean("available");
                                    if(isAvailable != null){
                                        if(isAvailable.equals(false)){
                                            Toast.makeText(getContext(), "Sorry, this session is not available", Toast.LENGTH_SHORT).show();
                                        }else{
                                            dataManager.create(user.getEmail(),"Player 2",selectedDate.toString(), time,1,1);
                                            Toast.makeText(getContext(), "Your booking has been recorded", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                            dataManager.create(user.getEmail(),"Player 2",selectedDate.toString(), time,1,1);
                                            Toast.makeText(getContext(), "Your booking has been recorded", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                            Toast.makeText(getContext(), "Your booking has been recorded", Toast.LENGTH_SHORT).show();
                                        dataManager.create(user.getEmail(),"pas moi",selectedDate.toString(), time,1,1);
                                }
                            }
                        });
            }
        });
        /* Return the completed view to render on screen. */
        return convertView;
    }

    //Retrieve the parent Index of a button (literally the day of a time button)
    public int getParentIndex(Button btn){
        View parentRow = (View)btn.getParent();
        GridView parentGrid = (GridView)parentRow.getParent();
        ListView listView = (ListView) parentGrid.getParent().getParent();
        Integer index = listView.getPositionForView(parentGrid);
        return index;
    }
}
