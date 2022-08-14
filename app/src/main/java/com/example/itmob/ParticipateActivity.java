package com.example.itmob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    ImageView imageView_courseBild;


    RecyclerView recyclerView ;

    TextView headingTv ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participate);

        date_top = findViewById(R.id.textView_kursdatum);
        nameOF_Course = findViewById(R.id.textView_kurstitel);
        instructorName_participate = findViewById(R.id.textView11_trainerName);
        participateBtn = findViewById(R.id.btnParticipate);
        headingTv = findViewById(R.id.textView10_kursBeschreibung);
        imageView_courseBild = findViewById(R.id.imageView_courseBild);


        dbHelper = new DBHelper(this);




        modelCourses = (ModelCourses) getIntent().getSerializableExtra("modelObject");
        email = getIntent().getStringExtra("email");

        dayInt = getIntent().getIntExtra("day",1);




        // setting status of Button which is by default green and text is to participate ,
        // but if user is already enrolled than in bellow lines button text is chained to roll out and
        // button's functionality is changed

        if (dbHelper.checkIfParticipated(modelCourses.getId(),email)){

            participateBtn.setText("Vom Kurs abmelden");
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


        String kursname = modelCourses.getName();
        String trainer = "Trainer: "+modelCourses.getTrainer();
        String startZeit = modelCourses.getStartTime();
        String kursDauer = modelCourses.getTimeDuration();
        String kurstag = day+","+modelCourses.getDate();

        ArrayList<String> courseData = new ArrayList<>();
        courseData = dbHelper.getCourseData(kursname);


        String kursBeschreibung = courseData.get(5);


        date_top.setText(kurstag);
        nameOF_Course.setText(kursname);
        instructorName_participate.setText(trainer);
        headingTv.setText(kursBeschreibung);

        if(kursname.equals("Pilates")){
            imageView_courseBild.setImageDrawable(getResources().getDrawable(R.drawable.pilates));
        }
        if(kursname.equals("BodyFit")){
            imageView_courseBild.setImageDrawable(getResources().getDrawable(R.drawable.bodyfit));
        }
        if(kursname.equals("RückenFit")){
            imageView_courseBild.setImageDrawable(getResources().getDrawable(R.drawable.rueckenfit));
        }
        if(kursname.equals("Zumba")){
            imageView_courseBild.setImageDrawable(getResources().getDrawable(R.drawable.zumba));
        }
        if(kursname.equals("Fatburner")){
            imageView_courseBild.setImageDrawable(getResources().getDrawable(R.drawable.fatburner));
        }
        if(kursname.equals("Bodyworkout")){
            imageView_courseBild.setImageDrawable(getResources().getDrawable(R.drawable.bodyfit));
        }
        if(kursname.equals("Bodypump")){
            imageView_courseBild.setImageDrawable(getResources().getDrawable(R.drawable.bodyfit));
        }
        if(kursname.equals("Bauch Special")){
            imageView_courseBild.setImageDrawable(getResources().getDrawable(R.drawable.sixpack));
        }
        if(kursname.equals("BBP Plus")){
            imageView_courseBild.setImageDrawable(getResources().getDrawable(R.drawable.bbp));
        }
        if(kursname.equals("Body Balance")){
            imageView_courseBild.setImageDrawable(getResources().getDrawable(R.drawable.balance));
        }



        participateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // if user has already enrolled than by click this button he will be unrolled

                if (dbHelper.checkIfParticipated(modelCourses.getId(),email)){

                        dbHelper.unRollUserFromCourse(modelCourses.getId());
                    Toast.makeText(ParticipateActivity.this, "Du hast dich vom Kurs abgemeldet.", Toast.LENGTH_SHORT).show();

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




    }
}