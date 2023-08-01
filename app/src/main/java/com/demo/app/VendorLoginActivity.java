package com.demo.app;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;


public class VendorLoginActivity extends AppCompatActivity {

    private EditText vnameEditText, passwordEditText;
    private Button loginButton;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_logpage);



        vnameEditText = findViewById(R.id.vnameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);


        loginButton = findViewById(R.id.loginButton);

        database = openOrCreateDatabase("eventsy.db", MODE_PRIVATE, null);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = vnameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                Cursor cursor = database.rawQuery("SELECT * FROM vendors WHERE vendorname=?", new String[]{username});


                if (cursor.moveToFirst()) {
                    String dbPassword;
                    dbPassword = cursor.getString(cursor.getColumnIndex("password"));

                    if (password.equals(dbPassword)) {
                        Intent intent = new Intent(VendorLoginActivity.this, VendorUI.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(VendorLoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(VendorLoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
            }
        });

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(VendorLoginActivity.this, new_signup_vendor.class);
                Intent[] intents = {intent};
                startActivities(intents);
            }
        });

    }

}