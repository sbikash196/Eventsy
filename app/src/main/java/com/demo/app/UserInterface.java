package com.demo.app;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import android.view.View;
import android.widget.ImageView;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;


public class UserInterface extends AppCompatActivity {

    private List<venue> venueList;

    private List<String> imageUrls;
    private RecyclerView recyclerView;
    private VenueAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

        recyclerView = findViewById(R.id.recyclerViewVenue);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        venueList = new ArrayList<>();
        imageUrls = new ArrayList<>();
        adapter = new VenueAdapter(this, venueList, imageUrls);
        recyclerView.setAdapter(adapter);

        ImageView addIconImageView = findViewById(R.id.addIconImageView1);
        addIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInterface.this, VenueRegistration.class);
                startActivity(intent);
            }
        });

        showVenuesInRecyclerView();
    }
        @Override
        protected void onResume() {
            super.onResume();
            showVenuesInRecyclerView();
        }
    public void deleteVenue(int venueId) {
        database db = new database(UserInterface.this);
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete("venues", "id=?", new String[]{String.valueOf(venueId)});
        sqLiteDatabase.close();
    }


    public List<venue> fetchAllVenues() {
        List<venue> venues = new ArrayList<>();

        database g = new database(UserInterface.this);
        SQLiteDatabase db = g.getReadableDatabase();
        Cursor cursor = db.query("venues", new String[]{"venue_name", "venue_location", "venue_price", "image_url"},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract values from the cursor
                String name = cursor.getString(cursor.getColumnIndexOrThrow("venue_name"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("venue_location"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("venue_price"));
                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_url"));

                // Create a new venue object with the extracted values
                venue venue = new venue(0, name, location, price, imageUrl);
                venues.add(venue);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return venues;
    }


    public void showVenuesInRecyclerView() {
        venueList.clear();
        imageUrls.clear();

        // Fetch the latest data from the database and populate the lists
        List<venue> newVenues = fetchAllVenues();
        venueList.addAll(newVenues);
        for (venue v : newVenues) {
            imageUrls.add(v.getImageUrl());
        }

        adapter.updateData(newVenues);
    }
    }






