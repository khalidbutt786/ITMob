package com.example.itmob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    EditText email, password;
    Button btnlogin;
    TextView registerLabel;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        registerLabel = findViewById(R.id.register_label);



        DB = new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = email.getText().toString();
                String pass = password.getText().toString();

                if(userEmail.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Bitte alle Felder angeben", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(userEmail, pass);
                    if(checkuserpass==true){
                        Toast.makeText(LoginActivity.this, "Anmeldung erfolgreich", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("Email", userEmail);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Falsche Anmeldedaten", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registerLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}