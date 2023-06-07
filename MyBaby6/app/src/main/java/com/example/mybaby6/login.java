package com.example.mybaby6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class login extends AppCompatActivity {
    Button signUpLOG, signInLOG;
    TextInputLayout enterNameLOG, enterPasswordLOG;
    private String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        signUpLOG = findViewById(R.id.signUpLOG);
        signInLOG = findViewById(R.id.signInLOG);
        enterPasswordLOG = findViewById(R.id.enterPasswordLOG);
        enterNameLOG = findViewById(R.id.enterNameLOG);
        enterPasswordLOG = findViewById(R.id.enterPasswordLOG);
        DB DB = new DB(this);
        signInLOG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = Objects.requireNonNull(enterNameLOG.getEditText()).getText().toString();
                pass = Objects.requireNonNull(enterPasswordLOG.getEditText()).getText().toString();
                if(user.equals("")||pass.equals(""))
                    Toast.makeText(login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass){
                        Toast.makeText(login.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), Main_Home_Page.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signUpLOG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), registration.class);
                startActivity(intent);
            }
        });

    }
}


