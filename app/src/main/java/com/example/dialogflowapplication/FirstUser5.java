package com.example.dialogflowapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FirstUser5 extends AppCompatActivity {
    private Button   continueButton;
    private EditText contactFirstName;
    private EditText contactLastName;
    private EditText contactPhoneNumber;
    private String   firstName;
    private String   lastName;
    private String   email;
    private String   password;
    private String   birthDate;
    private String   gender;

    private TextInputLayout firstNameContactLayout;
    private TextInputLayout lastNameContactLayout;
    private TextInputLayout phoneContactLayout;

    private FirebaseAuth      firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user1_sign_up5);
        setUpViews();
        getDataFromPreviousActivity();
        setUpClickListeners();
    }

    public boolean checkInput(){
        if(contactFirstName.getText().toString().trim().isEmpty() &&
           contactLastName.getText().toString().trim().isEmpty()  &&
           contactPhoneNumber.getText().toString().trim().isEmpty()){
            firstNameContactLayout.setError("Field cannot be empty");
            lastNameContactLayout.setError("Field cannot be empty");
            phoneContactLayout.setError("Field cannot be empty");
            return false;
        }

        if(contactFirstName.getText().toString().trim().isEmpty()){
            firstNameContactLayout.setError("Field cannot be empty");
            lastNameContactLayout.setErrorEnabled(false);
            phoneContactLayout.setErrorEnabled(false);
            return false;
        }

        else if(contactLastName.getText().toString().trim().isEmpty()){
            lastNameContactLayout.setError("Field cannot be empty");
            firstNameContactLayout.setErrorEnabled(false);
            phoneContactLayout.setErrorEnabled(false);
            return false;
        }

        else if(contactPhoneNumber.getText().toString().trim().isEmpty()){
            phoneContactLayout.setError("Field cannot be empty");
            firstNameContactLayout.setErrorEnabled(false);
            lastNameContactLayout.setErrorEnabled(false);
            return false;
        }
        else {
            phoneContactLayout.setError(null);
            firstNameContactLayout.setError(null);
            lastNameContactLayout.setError(null);
            return true;
        }
    }

    public void getDataFromPreviousActivity(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            firstName = extras.getString("firstName");
            lastName  = extras.getString("lastName");
            email     = extras.getString("email");
            password  = extras.getString("password");
            birthDate = extras.getString("birthDate");
            gender    = extras.getString("gender");
        }
    }

    public void saveDataToFireStore(){
        firestore = FirebaseFirestore.getInstance();
        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = firestore.collection("users").document(userID);
        Map<String, Object> userData = new HashMap<>();
        userData.put("firstName", firstName);
        userData.put("lastName", lastName);
        userData.put("email", email);
        userData.put("birthDate", birthDate);
        userData.put("gender", gender);
        userData.put("contactFirstName",   contactFirstName.getText().toString().trim());
        userData.put("contactLastName",    contactLastName.getText().toString().trim());
        userData.put("contactPhoneNumber", contactPhoneNumber.getText().toString().trim());
        documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "onSuccess: User Profile is create for " + userID);
            }
        });
    }

    public void setUpViews(){
        continueButton         = findViewById(R.id.button);
        contactFirstName       = findViewById(R.id.contactFirstName);
        contactLastName        = findViewById(R.id.contactLastName);
        contactPhoneNumber     = findViewById(R.id.contactPhoneNumber);
        firstNameContactLayout = findViewById(R.id.textInput2);
        lastNameContactLayout  = findViewById(R.id.textInput1);
        phoneContactLayout     = findViewById(R.id.textInput3);
    }

    public void setUpClickListeners(){
        firebaseAuth = FirebaseAuth.getInstance();
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                saveDataToFireStore();
                                Intent intent = new Intent(FirstUser5.this, DrawerActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finishAffinity();   // remove all of the previous activities from the back-stack of application
                            } else{
                                Snackbar.make(v, "Failed to register you as user", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}