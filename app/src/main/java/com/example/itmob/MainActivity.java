package com.example.itmob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*

Used Images for Demo purposes:

https://unsplash.com/photos/JwYsUx7JyuA Gustavo Torres November 9, 2018

https://unsplash.com/photos/3qZt1MwF4Zo Matthew Sichkaruk Published on January 6, 2021

https://unsplash.com/photos/FodEsaNZs48 Luis Vidal Published on January 27, 2020

https://unsplash.com/photos/lrQPTQs7nQQ Jonathan Borba Published on October 14, 2019

https://unsplash.com/photos/A_ftsTh53lM Trust "Tru" Katsande Published on March 12, 2018

https://unsplash.com/photos/gJtDg6WfMlQ bruce mars Published on February 14, 2018

https://unsplash.com/photos/buWcS7G1_28 Meghan Holmes Published on August 14, 2018

https://unsplash.com/photos/YJdCZba0TYE Dylan Gillis Published on May 1, 2020

https://unsplash.com/photos/y0SMHt74yqc bruce mars Published on February 11, 2018*/

public class MainActivity extends AppCompatActivity {

    EditText email, password, repassword, vertragsID;
    Button signup;
    TextView signin;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = (EditText) findViewById(R.id.inputUsername);
        vertragsID = (EditText) findViewById(R.id.mitgliedsnrInput);

        password = (EditText) findViewById(R.id.inputPassword);
        repassword = (EditText) findViewById(R.id.inputPasswordRetype);
        signup = (Button) findViewById(R.id.button4);
        signin = (TextView) findViewById(R.id.textView6);
        dbHelper = new DBHelper(this);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String vertragsID_user = vertragsID.getText().toString();
                String useremail = email.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();


                if(useremail.equals("")||pass.equals("")||repass.equals("")|| vertragsID_user.equals(""))
                    Toast.makeText(MainActivity.this, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = dbHelper.checkusername(useremail);
                        Boolean accountwithvertragIDaleradyExists = dbHelper.checkvertrag(useremail, vertragsID_user);

                        if(checkuser==false && accountwithvertragIDaleradyExists==true){
                            Boolean insert = dbHelper.insertDataUser(useremail,pass, Integer.parseInt(vertragsID_user));
                            if(insert==true){
                                Toast.makeText(MainActivity.this, "Registrierung erfolgreich", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this, "Registrierung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Angegebenen Daten konnten keinem Vertrag zugeordnet werden oder Vertrag hat bereits ein Konto", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Passwörter nicht identisch", Toast.LENGTH_SHORT).show();
                    }
                } }


        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}