package com.lamra.todoapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lamra.todoapp.BackEnd.DatabaseUserDetails;
import com.lamra.todoapp.R;

public class LogIn extends AppCompatActivity {

    private TextView dontHaveAccount;
    private TextInputEditText username,password;
    private DatabaseUserDetails db;
    private TextView login;
    public static String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        db = new DatabaseUserDetails(this);

        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginButton);
        dontHaveAccount = findViewById(R.id.dontHaveAccount);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkuser = db.checkUserNamePassword(user,pass);
                    if(checkuser){
                        Toast.makeText(getApplicationContext(), "LogIn Successful", Toast.LENGTH_SHORT).show();
                        MainActivity.activeUser = true;
                        UserName = user;
                        Toast.makeText(getApplicationContext(), UserName, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LogIn.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "LogIn Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }
}