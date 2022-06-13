package com.example.itmob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {


    CardView schritte, workout, community, geraeteanweisung, nfc, vertragsdaten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String username = getIntent().getStringExtra("Email");

        schritte = findViewById(R.id.schritte);
        workout = findViewById(R.id.workout);
        community = findViewById(R.id.community);
        geraeteanweisung = findViewById(R.id.geraeteanweisung);
        nfc = findViewById(R.id.nfc);
        vertragsdaten = findViewById(R.id.vertragsdaten);

        schritte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "schritte page", Toast.LENGTH_SHORT).show();
            }
        });

        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "workout page", Toast.LENGTH_SHORT).show();
            }
        });

        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "community page", Toast.LENGTH_SHORT).show();

            }
        });

        geraeteanweisung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "geraeteanweisung page", Toast.LENGTH_SHORT).show();

            }
        });

        nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "nfc page", Toast.LENGTH_SHORT).show();

            }
        });

        vertragsdaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "vertragsdaten page", Toast.LENGTH_SHORT).show();

            }
        });





    }
}