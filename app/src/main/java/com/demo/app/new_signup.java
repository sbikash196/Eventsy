package com.demo.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;




public class new_signup extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, emailEditText, confirmPasswordEditText, phoneEditText;
    private Button registerButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_signup);

        database g = new database(new_signup.this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText= findViewById(R.id.emailEditText);
        confirmPasswordEditText= findViewById(R.id.confirmPasswordEditText);
        phoneEditText= findViewById(R.id.phoneEditText);

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username=usernameEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                String cPassword =confirmPasswordEditText.getText().toString();
                String email=emailEditText.getText().toString();
                String phone=phoneEditText.getText().toString();
                if(username.equals("")||password.equals("")||cPassword.equals("")||email.equals("")||phone.equals("")){
                    Toast.makeText(new_signup.this, "Please fill out all the textfields!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (password.equals(cPassword)) {

                        boolean i = g.insert_data(username, password, email, phone);

                        if (i == true) {
                            String emailPattern = "[a-zA-Z0-9._-]+@+[a-zA-Z0-9._-]+.com";
                            if (!email.matches(emailPattern)) {
                                Toast.makeText(new_signup.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(new_signup.this, "User Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(new_signup.this, MainActivity.class);
                                finish();
                            }

                        } else {
                            Toast.makeText(new_signup.this, "password do not match", Toast.LENGTH_SHORT).show();
                        }
                    }

                    }

                    }


            });
        }
    }