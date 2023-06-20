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


public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);


        loginButton = findViewById(R.id.loginButton);

        database = openOrCreateDatabase("eventsy.db", MODE_PRIVATE, null);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Query the database to fetch user record based on the entered username
                Cursor cursor = database.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});

                if (cursor.moveToFirst()) {
                    String dbPassword;
                    dbPassword = cursor.getString(cursor.getColumnIndex("password"));

                    if (password.equals(dbPassword)) {
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
            }
        });

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, new_signup.class);
                Intent[] intents = {intent};
                startActivities(intents);
            }
        });

    }

}