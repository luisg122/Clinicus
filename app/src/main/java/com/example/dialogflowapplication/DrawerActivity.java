package com.example.dialogflowapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.*;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ArrayList<String> mTiles;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;

   @RequiresApi(api = Build.VERSION_CODES.KITKAT)
   @Override
   protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.drawer_layout);
       setUpViews();
       setUpDrawer();
       setupDrawerContent(navigationView);
       FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
       ft.replace(R.id.frame, new HomePage());
       ft.commit();
       navigationView.setCheckedItem(R.id.home);
   }

   public void setUpViews(){
       navigationView = findViewById(R.id.navView);
       toolbar = findViewById(R.id.toolbar);
       drawer = findViewById(R.id.drawer_layout);
   }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setUpDrawer() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.skyBlue));
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    private void setupDrawerContent(@NonNull NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
       int id = item.getItemId();
       switch (id){
           case R.id.home:
               FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
               ft.replace(R.id.frame, new HomePage());
               ft.commit();
               break;
           /*case R.id.bookmarks:
               Intent intent = new Intent(HomePage.this, SeeTherapistActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
               startActivity(intent);
               break;
           case R.id.Questions:
               Intent intent = new Intent(HomePage.this, SeeTherapistActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
               startActivity(intent);
               break;*/
           case R.id.logout:
               FirebaseAuth.getInstance().signOut();
               Intent intent = new Intent(DrawerActivity.this, FirstScreen.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
               startActivity(intent);
               break;
       }

       drawer.closeDrawer(GravityCompat.START);
       return true;
    }
}