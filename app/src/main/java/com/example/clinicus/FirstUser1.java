package com.example.clinicus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstUser1 extends AppCompatActivity {
    private Button continueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user1_sign_up1);
        setUpViews();
        setUpClickListeners();
    }

    public void setUpViews(){
        continueButton = findViewById(R.id.button);
    }

    public void setUpClickListeners(){
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstUser1.this, FirstUser2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}
