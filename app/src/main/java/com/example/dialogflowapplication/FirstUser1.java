package com.example.dialogflowapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class FirstUser1 extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private Button continueButton;
    private TextInputLayout firstNameLayout;
    private TextInputLayout lastNameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user1_sign_up1);
        setUpViews();
        setUpClickListeners();
    }

    public void setUpViews(){
        continueButton  = findViewById(R.id.button);
        firstName       = findViewById(R.id.firstName);
        lastName        = findViewById(R.id.lastName);
        firstNameLayout = findViewById(R.id.firstName_TextInputLayout);
        lastNameLayout  = findViewById(R.id.lastName_TextInputLayout);
    }

    public boolean checkInput(){
        if (firstName.getText().toString().trim().isEmpty() &&
                lastName.getText().toString().trim().isEmpty()){
            firstNameLayout.setError("Field cannot be empty");
            lastNameLayout.setError("Field cannot be empty");
            return false;
        }

        else if(firstName.getText().toString().trim().isEmpty()){
            firstNameLayout.setError("Field cannot be empty");
            lastNameLayout.setErrorEnabled(false);
            return false;
        }

        else if (lastName.getText().toString().trim().isEmpty()){
            lastNameLayout.setError("Field cannot be empty");
            firstNameLayout.setErrorEnabled(false);
            return false;
        }

        else {
            firstNameLayout.setError(null);
            lastNameLayout.setError(null);
            return true;
        }
    }

    public void setUpClickListeners(){
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    Intent intent = new Intent(FirstUser1.this, FirstUser2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("firstName", firstName.getText().toString().trim());
                    intent.putExtra("lastName", lastName.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });
    }
}