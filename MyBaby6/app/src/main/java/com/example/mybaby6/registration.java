package com.example.mybaby6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class registration extends AppCompatActivity {
    TextInputLayout enterName, enterPassword, enterPasswordAgain;
    private String user, pass, repass;
    DB DB;
    Button signUp, alreadyRegistered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        signUp = findViewById(R.id.signUp);
        alreadyRegistered = findViewById(R.id.alreadyRegistered);
        enterName = findViewById(R.id.enterName);
        enterPassword = findViewById(R.id.enterPassword);
        enterPasswordAgain = findViewById(R.id.enterPasswordAgain);
        DB = new DB(this);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = Objects.requireNonNull(enterName.getEditText()).getText().toString();
                pass = Objects.requireNonNull(enterPassword.getEditText()).getText().toString();
                repass = Objects.requireNonNull(enterPasswordAgain.getEditText()).getText().toString();
                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(registration.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser==false){
                            Boolean insert = DB.insertData(user, pass);
                            if(insert==true){
                                Toast.makeText(registration.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Main_Home_Page.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(registration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(registration.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(registration.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                } }
        });
        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });
    }
}