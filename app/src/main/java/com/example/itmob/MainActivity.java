package com.example.itmob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText email, password, repassword, vertragsID;
    Button signup, signin;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        vertragsID = (EditText) findViewById(R.id.vertragsID);

        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);
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
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Registrierung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Angegebenen Daten konnten keinem Vertrag zugeordnet werden", Toast.LENGTH_SHORT).show();
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

            }
        });

    }
}