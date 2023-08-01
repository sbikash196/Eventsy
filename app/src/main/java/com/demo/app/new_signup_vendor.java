package com.demo.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;




public class new_signup_vendor extends AppCompatActivity {

    private EditText vNameEditText, organizationEditText, passwordEditText, emailEditText, confirmPasswordEditText, phoneEditText;
    private Button registerButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_signup_vendor);

        database g = new database(new_signup_vendor.this);

        vNameEditText=findViewById(R.id.vNameEditText);
        organizationEditText= findViewById(R.id.organizationEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText= findViewById(R.id.emailEditText);
        confirmPasswordEditText= findViewById(R.id.confirmPasswordEditText);
        phoneEditText= findViewById(R.id.phoneEditText);

        Button registerButton = findViewById(R.id.vendorRegister);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String vName=vNameEditText.getText().toString();
                String organization=organizationEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                String cPassword =confirmPasswordEditText.getText().toString();
                String email=emailEditText.getText().toString();
                String phone=phoneEditText.getText().toString();
                if(vName.equals("")||organization.equals("")||password.equals("")||cPassword.equals("")||email.equals("")||phone.equals("")){
                    Toast.makeText(new_signup_vendor.this, "Please fill out all the textfields!!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                        if (password.equals(cPassword)) {
                            database db = new database(new_signup_vendor.this);
                            boolean i = db.insertVendorData(vName, organization, password, email, phone);
                            db.close(); // Close the database after use

                            if (i) {
                                // User registration successful
                                String emailPattern = "[a-zA-Z0-9._-]+@+[a-zA-Z0-9._-]+.com";
                                if (!email.matches(emailPattern)) {
                                    Toast.makeText(new_signup_vendor.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(new_signup_vendor.this, "Vendor Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(new_signup_vendor.this, UserInterface.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                // Vendor registration failed
                                Toast.makeText(new_signup_vendor.this, "Vendor registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(new_signup_vendor.this, "Password do not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
    }
}