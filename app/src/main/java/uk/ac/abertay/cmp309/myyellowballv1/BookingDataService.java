package uk.ac.abertay.cmp309.myyellowballv1;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDataService {
    private Context context;
    private FirebaseFirestore db;

    public BookingDataService(Context context) {
        this.context = context;
    }

    public List<Integer> getAvailableTimetableByDay(Date date, int courtNumber){
    return null;
    }

    public void create(String player1, String player2, String date, int time, int courtNumber, int clubID){
        db = FirebaseFirestore.getInstance();
        Map<String, Object> booking = new HashMap<>();
        booking.put("available", false);
        booking.put("player_1", player1);
        booking.put("player_2", player2);
        booking.put("time", time);

        db.collection("Clubs")
                .document(String.valueOf(clubID))
                .collection("courts")
                .document("court_"+String.valueOf(courtNumber))
                .collection("bookings")
                .document(date)
                .collection("times")
                .document(String.valueOf(time))
                .set(booking)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
    public void read(Date date, int time, int courtNumber, int clubID){

    }

    public void update(int player1ID, int player2ID, Date date, int time, int courtNumber, int clubID){

    }

    public void delete(Date date, int time, int courtNumber, int clubID){

    }

}
