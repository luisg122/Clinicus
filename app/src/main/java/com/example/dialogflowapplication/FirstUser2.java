package com.example.dialogflowapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstUser2 extends AppCompatActivity {
    private Button   continueButton;
    private EditText email;
    private EditText password;
    private String   firstName;
    private String   lastName;

    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user1_sign_up2);
        setUpViews();
        getDataFromPreviousActivity();
        setUpClickListeners();
    }

    public void getDataFromPreviousActivity(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            firstName = extras.getString("firstName");
            lastName  = extras.getString("lastName");
        }
    }

    public boolean checkInput(){
        if(email.getText().toString().trim().isEmpty() &&
           password.getText().toString().trim().isEmpty()){
            emailLayout.setError("Field cannot be empty");
            passwordLayout.setError("Field cannot be empty");
            return false;
        }

        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
            emailLayout.setError("Please enter a valid email address");
            passwordLayout.setErrorEnabled(false);
            return false;
        }

        else if(email.getText().toString().trim().isEmpty()){
            emailLayout.setError("Field cannot be empty");
            passwordLayout.setErrorEnabled(false);
            return false;
        }

        else if(password.getText().toString().trim().isEmpty()){
            passwordLayout.setError("Field cannot be empty");
            emailLayout.setErrorEnabled(false);
            return false;
        }

        else if(password.getText().toString().trim().length() < 8
                && !isValidPassword(password.getText().toString().trim())){
            passwordLayout.setError("Please enter a valid password with 8 or more characters");
            emailLayout.setErrorEnabled(false);
            return false;
        }

        /*else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches() &&
                (password.getText().toString().trim().length() < 8
                && !isValidPassword(password.getText().toString().trim()))){
            emailLayout.setError("Please enter a valid email address");
            passwordLayout.setError("Please enter a valid password with 8 or more characters");
            return false;
        }*/

        else {
            emailLayout.setError(null);
            passwordLayout.setError(null);
            return true;
        }
    }

    public boolean isValidPassword(String password){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public void setUpViews(){
        continueButton = findViewById(R.id.button);
        email          = findViewById(R.id.email);
        password       = findViewById(R.id.password);
        emailLayout    = findViewById(R.id.email_TextInputLayout);
        passwordLayout = findViewById(R.id.password_TextInputLayout);
    }

    public void setUpClickListeners(){
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    Intent intent = new Intent(FirstUser2.this, FirstUser3.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("firstName", firstName);
                    intent.putExtra("lastName", lastName);
                    intent.putExtra("email", email.getText().toString().trim());
                    intent.putExtra("password", password.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });
    }
}