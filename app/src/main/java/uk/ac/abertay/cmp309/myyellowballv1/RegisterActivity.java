package uk.ac.abertay.cmp309.myyellowballv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String FIRESTORE_TAG = "FIRESTORE";

    private TextView login;
    private FirebaseAuth mAuth;
    private Button btnRegister;
    private FirebaseFirestore db;
    private EditText etFirstName, etLastName, etEmail, etPassword, etClubID;
    private int clubID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialise Firebase Auth and the database to store the user information
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etLastName = findViewById(R.id.etLastName);
        etFirstName = findViewById(R.id.etFirstName);
        etClubID = findViewById(R.id.etClubID);
        etEmail = findViewById(R.id.etEmail_register);
        etPassword = findViewById(R.id.etPassword_register);
        btnRegister = findViewById(R.id.btnRegister);
        login = findViewById(R.id.tvLogin_register);


        login.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                registerUser();
                break;
            case R.id.tvLogin_register:
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
        }
    }

    private void registerUser() {
        String lastName = etLastName.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String stringClubID = etClubID.getText().toString().trim();
        if (!stringClubID.isEmpty()) {
            clubID = Integer.parseInt(etClubID.getText().toString().trim());
        }
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        //Fields that need to be checked if empty
        ArrayList<EditText> fieldsToCheckEmpty = new ArrayList<EditText>();
        fieldsToCheckEmpty.add(etLastName);
        fieldsToCheckEmpty.add(etFirstName);
        fieldsToCheckEmpty.add(etClubID);
        fieldsToCheckEmpty.add(etEmail);
        fieldsToCheckEmpty.add(etPassword);

        //Fields that need other verifications
        ArrayList<EditText> fieldsToCheckComplementary = new ArrayList<EditText>();
        fieldsToCheckComplementary.add(etEmail);
        fieldsToCheckComplementary.add(etPassword);


        if (!Utils.checkNotEmpty(RegisterActivity.this, fieldsToCheckEmpty)) {
            return;
        }
        if (!Utils.checkComplementary(RegisterActivity.this, fieldsToCheckComplementary)) {
            return;
        }

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(lastName, firstName, email, clubID);
                            db.collection("Users")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                            Log.d(FIRESTORE_TAG, "Document added with ID: " + documentReference.getId());
                                            Intent intent = new Intent().putExtra("email", email);
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, "Failed to register user, please try again", Toast.LENGTH_SHORT).show();
                                            Log.w(FIRESTORE_TAG, "Error adding document", e);
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to register user n2, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}