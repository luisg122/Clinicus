package com.example.dialogflowapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FirstUser3 extends AppCompatActivity {
    private Button     continueButton;
    private DatePicker datePicker;
    private String     firstName;
    private String     lastName;
    private String     email;
    private String     password;
    private String     birthDate;

    private int mYear = 0, mMonth = 0, mDay = 0;
    private static final String TAG = "Birthdate";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user1_sign_up3);
        setUpViews();
        getDataFromPreviousActivity();
        datePicker();
        setUpClickListeners();
    }

    public void getDataFromPreviousActivity(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            firstName = extras.getString("firstName");
            lastName  = extras.getString("lastName");
            email     = extras.getString("email");
            password  = extras.getString("password");
        }
    }

    public void setUpViews(){
        continueButton = findViewById(R.id.button);
        datePicker     = findViewById(R.id.datePicker);
    }

    public void datePicker(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                mYear  = year;
                mMonth = month;
                mDay   = dayOfMonth;
                updateDisplay();
            }
        });
    }

    public void updateDisplay(){
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newDate = Calendar.getInstance();
        newDate.set(mYear, mMonth, mDay);
        birthDate = dateFormatter.format(newDate.getTime());
        Log.d(TAG, birthDate);
    }

    public void setUpClickListeners(){
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstUser3.this, FirstUser4.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("birthDate", birthDate);
                startActivity(intent);
            }
        });
    }
}

    /*public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }*/

