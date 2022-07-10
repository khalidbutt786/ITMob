package com.example.itmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {



    BottomNavigationView bottomNavigationView;

    NFC nfcFragment = new NFC();
    Profil profilFragment = new Profil();
    QRCamera qrCameraFragment = new QRCamera();
    Community communityFragment = new Community();
    Workout workoutFragment = new Workout();

    String username, id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = getIntent().getStringExtra("Email");
        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setSelectedItemId(R.id.nfc);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, nfcFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.nfc:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, nfcFragment, "FRGNFC").commit();
                        return true;
                    case R.id.profil:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profilFragment, "FRGPROFIL").addToBackStack(null).commit();
                        return true;
                    case R.id.community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, communityFragment, "FRGCOMMUNITY").commit();
                        return true;
                    case R.id.workout:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, workoutFragment, "FRGWORKOUT").commit();
                        return true;
                    case R.id.qrcamera:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, qrCameraFragment, "FRGCAMERA").commit();

                        return true;
                }

                return false;
            }
        });







    }

    public String getUsername(){
        return username;
    }
    public String getId(){
        return id;
    }
}
