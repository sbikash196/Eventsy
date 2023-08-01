package com.demo.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button loguser = findViewById(R.id.loguser);
        loguser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button logvendor = findViewById(R.id.logvendor);
        logvendor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Home.this, VendorLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
