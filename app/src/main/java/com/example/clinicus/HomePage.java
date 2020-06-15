package com.example.clinicus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class HomePage extends Fragment {
    private Toolbar  toolbar;
    private LinearLayout linearLayout;
    private CardView  chatbotCard;
    private CardView  questionCard;
    private CardView  therapistCard;
    private CardView  groupCard;
    private ImageView imageView;
    private View view;

    public HomePage(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.home_options, container, false);
        setViews();
        //setUpToolbar();
        clickOnCards();
        return view;
    }

    private void setViews(){
        //toolbar = view.findViewById(R.id.toolbar);
        //linearLayout  = view.findViewById(R.id.linearLayoutIncluded);
        chatbotCard   = view.findViewById(R.id.chatBoxSpace);
        questionCard  = view.findViewById(R.id.askQuestionSpace);
        therapistCard = view.findViewById(R.id.seeTherapist);
        groupCard     = view.findViewById(R.id.groupSpace);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setUpToolbar(){
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toolbar.setTitle("Clinicus");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_icon_inflate, menu);
        MenuItem menuItem = menu.findItem(R.id.addNotes);
        View view = menuItem.getActionView();
        imageView = view.findViewById(R.id.notesIcon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserNotes.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void clickOnCards(){
        chatbotCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Chatbot.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        questionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        therapistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeeTherapistActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        groupCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JoinGroups.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}