package com.example.dialogflowapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AskQuestionActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        setUpViews();
        setUpToolbar();
    }

    public void setUpViews(){
        toolbar = findViewById(R.id.toolbar);
    }

    public void setUpToolbar(){
        toolbar.setTitle("Ask away");
        toolbar.setNavigationIcon(R.drawable.ic_white_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
