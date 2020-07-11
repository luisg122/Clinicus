package com.example.dialogflowapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.dialogflowapplication.Database.NotesDatabase;
import com.example.dialogflowapplication.Entities.Note;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserNotes extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView backImage;
    private ImageView saveImage;
    private EditText  inputNoteTitle;
    private EditText  inputNoteSubtitle;
    private EditText  inputNoteText;
    private TextView  textDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note_activity);
        setViews();
        setUpBackArrowListener();
        setNoteDateAndTime();
        saveImage();
        //setUpToolbar();
    }

    public void setViews(){
        //toolbar = findViewById(R.id.toolbar);
        backImage         = findViewById(R.id.imageBack);
        saveImage         = findViewById(R.id.imageSave);
        inputNoteTitle    = findViewById(R.id.inputNoteTitle);
        inputNoteSubtitle = findViewById(R.id.inputNoteSubtitle);
        inputNoteText     = findViewById(R.id.inputNoteText);
        textDateTime      = findViewById(R.id.textDateTime);
    }

    public void setNoteDateAndTime(){
        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date())
        );
    }

    public boolean checkNote() {
        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayout);
        if (inputNoteTitle.getText().toString().trim().isEmpty()
                && inputNoteSubtitle.getText().toString().trim().isEmpty()
                && inputNoteText.getText().toString().trim().isEmpty()) {
            Snackbar.make(coordinatorLayout, "Fields cannot be empty", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (inputNoteTitle.getText().toString().trim().isEmpty()) {
            Snackbar.make(coordinatorLayout, "Note Title cannot be empty", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (inputNoteSubtitle.getText().toString().trim().isEmpty()) {
            Snackbar.make(coordinatorLayout, "Note Subtitle cannot be empty", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (inputNoteText.getText().toString().trim().isEmpty()) {
            Snackbar.make(coordinatorLayout, "Note cannot be empty", Snackbar.LENGTH_LONG).show();
            return false;
        } else{
            return true;
        }
    }

    public void createNote(){
        final Note note = new Note();
        note.setTitle(inputNoteTitle.getText().toString().trim());
        note.setSubtitle(inputNoteSubtitle.getText().toString().trim());
        note.setNoteText(inputNoteText.getText().toString().trim());
        note.setDateTime(textDateTime.getText().toString().trim());

        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids){
                NotesDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        new SaveNoteTask().execute();
    }

    public void saveImage(){
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNote()){
                    createNote();
                }
            }
        });
    }

    public void setUpBackArrowListener(){
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setUpToolbar(){
        toolbar.setTitle("Your notes");
        toolbar.setNavigationIcon(R.drawable.ic_white_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}