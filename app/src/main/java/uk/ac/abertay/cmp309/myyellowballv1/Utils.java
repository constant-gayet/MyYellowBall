package uk.ac.abertay.cmp309.myyellowballv1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Utils {

    public static boolean checkNotEmpty(Context c,List<EditText> fields){
        for(EditText field:fields){
            String input = field.getText().toString().trim();
            if(input.isEmpty()){
                //Display a message of error
                field.setError(field.getHint().toString().trim() + " is required");
                field.requestFocus();
                return false;
            }
        }
        return true;
    }

    public static boolean checkComplementary(Context c, List<EditText> fields){
        for(EditText field : fields){
            String input = field.getText().toString().trim();
            String email = c.getString(R.string.e_mail);
            String password = c.getString(R.string.password);
            if(field.getHint().equals(email)){
                if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                    field.setError("Please provide valid email");
                    field.requestFocus();
                    return false;
                }
            }
            else if (field.getHint().equals(password)){
                if (input.length()<6) {
                    field.setError("Password should be at least 6 characters");
                    field.requestFocus();
                    return false;
                }
            }
        }
        return true;
    }
}
