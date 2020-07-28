package com.example.dialogflowapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QuestionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_space);
        setViews();
        setUpToolbar();
        openNewActivity();
    }

    public void setViews(){
        toolbar = findViewById(R.id.toolbar);
        fab     = findViewById(R.id.FAB);
    }

    public void setUpToolbar(){
        toolbar.setTitle("Questions");
        toolbar.setNavigationIcon(R.drawable.ic_white_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void openNewActivity(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionActivity.this, AskQuestionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}
