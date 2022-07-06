package com.example.itmob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class ParticipateActivity extends AppCompatActivity {


    ModelCourses modelCourses ;
    int dayInt ;
    String day="";

    TextView day_top;
    TextView date_top,nameOF_Course,instructorName_participate,time_participate;
    Button participateBtn;
    String email ;
    DBHelper dbHelper ;


    RecyclerView recyclerView ;

    TextView headingTv ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participate);

        day_top = findViewById(R.id.day_top);
        date_top = findViewById(R.id.date_top);
        nameOF_Course = findViewById(R.id.nameOF_Course);
        instructorName_participate = findViewById(R.id.instructorName_participate);
        time_participate = findViewById(R.id.time_participate);
        participateBtn = findViewById(R.id.btnParticipate);
        recyclerView = findViewById(R.id.recycler_part);
        headingTv = findViewById(R.id.participants_hard);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dbHelper = new DBHelper(this);




        modelCourses = (ModelCourses) getIntent().getSerializableExtra("modelObject");
        email = getIntent().getStringExtra("email");

        dayInt = getIntent().getIntExtra("day",1);




        // setting status of Button which is by default green and text is to participate ,
        // but if user is already enrolled than in bellow lines button text is chained to roll out and
        // button's functionality is changed

        if (dbHelper.checkIfParticipated(modelCourses.getId(),email)){

            participateBtn.setText("Un Roll");
            participateBtn.setBackgroundColor(Color.parseColor("#FF0000"));


        }


            // day got from previous activity from calendar was int so converting it into string day


            switch (dayInt){
            case 1:
                day="Monday";
                        break;
            case  2:
                day="Tuesday";
                break;

            case  3:
                day="Wednesday";
                break;

            case  4:
                day="Thursday";
                break;
            case  5:
                day="Friday";
                break;

            case  6:
                day="Saturday";
                break;

            case 7:
                day="Sunday";
                break;


        }


        day_top.setText(day);
        date_top.setText(modelCourses.getDate());
        nameOF_Course.setText(modelCourses.getName());
        instructorName_participate.setText("By   "+modelCourses.getTrainer());
        time_participate.setText(modelCourses.getStartTime());




        participateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // if user has already enrolled than by click this button he will be unrolled

                if (dbHelper.checkIfParticipated(modelCourses.getId(),email)){

                        dbHelper.unRollUserFromCourse(modelCourses.getId());
                    Toast.makeText(ParticipateActivity.this, "You've unrolled from the course", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ParticipateActivity.this , HomeActivity.class);
                        intent.putExtra("Email",email);
                        startActivity(intent);
                        finish();



                }
                // else if he is not enrolled than user can participate
                else{

                   if (dbHelper.insertParticipant(Integer.parseInt(modelCourses.getId().trim()),email)){

                       Intent intent = new Intent(ParticipateActivity.this , HomeActivity.class);
                       intent.putExtra("Email",email);
                       startActivity(intent);
                       finish();

                   }



                }




            }
        });


        // Emails of All Participants

       List<String>  list = dbHelper.gettingAllParticipants(modelCourses.getId());


       // Getting Names of All Participants

        if (list.size()>0){    // if participants are available than showing in recyclerView

            List<String> listOfNames = new ArrayList<>();

            for (int i = 0 ; i<list.size() ; i++){

                listOfNames.add( dbHelper.getParticipantsName(list.get(i)));

            }

            recyclerView.setAdapter(new AdapterParticipants(listOfNames , ParticipateActivity.this));



        }else{
            headingTv.setVisibility(View.INVISIBLE); // if no participants available
        }





    }
}