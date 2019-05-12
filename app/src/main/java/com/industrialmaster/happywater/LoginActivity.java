package com.industrialmaster.happywater;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);

        if(user.contains("EMAIL")&&user.contains("PASSWORD")){
            EditText emailtxt = findViewById(R.id.emailtxt);
            EditText passwordtxt = findViewById(R.id.passwordtxt);

            String email = emailtxt.getText().toString();
            String password = passwordtxt.getText().toString();

            String emailAlready = user.getString("EMAIL", "example@gmail.com");
            String passwordAlready = user.getString("PASSWORD", "guest");

            if(email.equals(emailAlready) && password.equals(passwordAlready)){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Email and/or Password Incorrect!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please Register to Login!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

    }

    public void register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
