package com.lamra.todoapp.Activities;

import static com.lamra.todoapp.Activities.LogIn.UserName;

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

public class SignUp extends AppCompatActivity {

    private TextInputEditText username,password,confirmPassword;
    private DatabaseUserDetails db;
    private TextView haveAccount;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        db = new DatabaseUserDetails(this);
        username = findViewById(R.id.signupUsername);
        password = findViewById(R.id.signupPassword);
        confirmPassword = findViewById(R.id.signupConfirmPassword);

        haveAccount = findViewById(R.id.signupHaveAccount);

        signup = findViewById(R.id.signupButton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = confirmPassword.getText().toString();
                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)|| TextUtils.isEmpty(repass)){
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }else{
                    if(pass.equals(repass)){
                        Boolean checkuser = db.checkUsser(user);
                        if(!checkuser){
                            Boolean insert = db.insertData(user,pass);
                            if(insert){
                                Toast.makeText(getApplicationContext(), "SignUp Successful", Toast.LENGTH_SHORT).show();
                                MainActivity.activeUser = true;
                                UserName = user;
                                Toast.makeText(getApplicationContext(), UserName, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                             }else{
                                Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        confirmPassword.setText("");
                        Toast.makeText(getApplicationContext(), "Confirm you password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,LogIn.class);
                startActivity(intent);
                finish();
            }
        });

    }
}