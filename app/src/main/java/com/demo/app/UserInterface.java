package com.demo.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class UserInterface extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar sidemenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_open, R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        sidemenu = findViewById(R.id.toolbar);
        sidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    openHomeActivity();
                } else if (itemId == R.id.login_menu) {
                    openMainActivity();
                } else if (itemId == R.id.signup) {
                    openRegistrationActivity();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void openHomeActivity() {
        Intent intent = new Intent(UserInterface.this, Home.class);
        startActivity(intent);
    }

    private void openMainActivity() {
        Intent intent = new Intent(UserInterface.this, MainActivity.class);
        startActivity(intent);
    }

    private void openRegistrationActivity() {
        Intent intent = new Intent(UserInterface.this, new_signup.class);
        startActivity(intent);
    }
}
