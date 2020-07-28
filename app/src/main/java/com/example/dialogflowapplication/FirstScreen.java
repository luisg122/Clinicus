package com.example.dialogflowapplication;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class FirstScreen extends AppCompatActivity {
    private VideoView videoView;
    private MediaPlayer mediaPlayer;
    private int currentVideoPosition;
    private Button signIn;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen_container);
        setUpViews();
        focusAudio();
        initiateVideo();
        setUpClickListeners();
    }

    public void setUpViews(){
        videoView = (VideoView) findViewById(R.id.videoView);
        signIn    = (Button) findViewById(R.id.signInButton);
        signUp    = (Button) findViewById(R.id.signUpButton);
    }

    public void focusAudio(){
        AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    public void setUpClickListeners(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstScreen.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(FirstScreen.this, FirstUser1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    public void initiateVideo(){
        Uri uri = Uri.parse("android.resource://"
                + getPackageName()
                + "/"
                + R.raw.startup_vid);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                mediaPlayer.setLooping(true);
                if(currentVideoPosition != 0){
                    mediaPlayer.seekTo(currentVideoPosition);
                    mediaPlayer.start();
                }
            }
        });
    }

    //Life Cycle of the activity holding the video page for sign-in or sign-up

    @Override
    protected void onPause(){
        super.onPause();
        currentVideoPosition = mediaPlayer.getCurrentPosition();
        videoView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        videoView.start();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}