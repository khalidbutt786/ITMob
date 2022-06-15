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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String username = getIntent().getStringExtra("Email");

        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setSelectedItemId(R.id.nfc);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, nfcFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.nfc:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, nfcFragment).commit();
                        return true;
                    case R.id.profil:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profilFragment).commit();
                        return true;
                    case R.id.community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, communityFragment).commit();
                        return true;
                    case R.id.workout:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, workoutFragment).commit();
                        return true;
                    case R.id.qrcamera:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, qrCameraFragment).commit();
                        return true;
                }

                return false;
            }
        });







    }
}