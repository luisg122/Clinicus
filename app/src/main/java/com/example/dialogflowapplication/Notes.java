package com.example.dialogflowapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.dialogflowapplication.Adapters.NotesAdapter;
import com.example.dialogflowapplication.Database.NotesDatabase;
import com.example.dialogflowapplication.Entities.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Notes extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private RecyclerView notesRecyclerView;
    private List<Note> notesList;
    private NotesAdapter notesAdapter;

    public  static  final int REQUEST_CODE_ADD_NOTE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_space);
        setUpViews();
        setUpToolbar();
        setUpRecyclerView();
        setUpClickListeners();
    }

    public void setUpViews(){
        toolbar              = findViewById(R.id.toolbar);
        notesRecyclerView    = findViewById(R.id.notesRecyclerView);
        floatingActionButton = findViewById(R.id.FAB);
    }

    public void setUpRecyclerView(){
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );

        notesList = new ArrayList<>();
        notesAdapter = new NotesAdapter(notesList);
        notesRecyclerView.setAdapter(notesAdapter);
        getNotes();
    }

    public void setUpToolbar(){
        toolbar.setTitle("Take down notes");
        toolbar.setNavigationIcon(R.drawable.ic_white_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setUpClickListeners(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notes.this, UserNotes.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void getNotes(){
        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {
            @Override
            protected List<Note> doInBackground(Void... voids){
                return NotesDatabase
                        .getDatabase(getApplicationContext())
                        .noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes){
                super.onPostExecute(notes);
                if(notesList.size() == 0){
                    notesList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();
                }
                else{
                    notesList.add(0, notes.get(0));
                }
                notesRecyclerView.smoothScrollToPosition(0);
                Log.d("My_Notes", notes.toString());
            }
        }
        new GetNotesTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK){
            getNotes();
        }
    }
}