package com.example.dialogflowapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FirstUser4 extends AppCompatActivity {
    private Button      continueButton;
    private RadioGroup  radioGroup;
    private RadioButton radioButton;
    private String      radioButtonString;
    private String      firstName;
    private String      lastName;
    private String      email;
    private String      password;
    private String      birthDate;

    private static final String TAG = "String";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user1_sign_up4);
        setUpViews();
        getDataFromPreviousActivity();
        getRadioButtonSelected();
        setUpClickListeners();
    }

    public void getDataFromPreviousActivity(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            firstName = extras.getString("firstName");
            lastName  = extras.getString("lastName");
            email     = extras.getString("email");
            password  = extras.getString("password");
            birthDate = extras.getString("birthDate");
        }
    }

    public void getRadioButtonSelected(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
                radioButtonString = radioButton.getText().toString().trim();
                checkString();
                Log.d(TAG, radioButtonString);
            }
        });
    }

    public void checkString(){
        if(radioButtonString.equals("Prefer not to say")){
            radioButtonString = "n/a";
        }
    }

    public void setUpViews(){
        continueButton = findViewById(R.id.button);
        radioGroup     = findViewById(R.id.radioGroup);
    }

    public void setUpClickListeners(){
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButtonString != null){
                    Intent intent = new Intent(FirstUser4.this, FirstUser5.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("firstName", firstName);
                    intent.putExtra("lastName", lastName);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("birthDate", birthDate);
                    intent.putExtra("gender", radioButtonString);
                    startActivity(intent);
                }
            }
        });
    }
}
/*
*/