package com.industrialmaster.happywater;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view){
        SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor userEditor = user.edit();

        EditText emailtxt = findViewById(R.id.emailtxt);
        String email = emailtxt.getText().toString();

        EditText passwordtxt = findViewById(R.id.passwordtxt);
        String password = passwordtxt.getText().toString();

        EditText passwordConfirmtxt = findViewById(R.id.passwordConfirmtxt);
        String passwordConfirm = passwordConfirmtxt.getText().toString();

        if(!password.equals(passwordConfirm)){
            Toast.makeText(this, "Passwords doesn't match!", Toast.LENGTH_SHORT).show();
            passwordtxt.setText("");
            passwordConfirmtxt.setText("");
        } else {
            userEditor.putString("EMAIL", email);
            userEditor.putString("PASSWORD", password);

            userEditor.apply();

            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MetaDataActivity.class);
            startActivity(intent);
        }
    }
}
