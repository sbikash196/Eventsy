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


public class UserInterface extends AppCompatActivity {

    private List<venue> venueList;
    private RecyclerView recyclerView;
    private VenueAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

        recyclerView = findViewById(R.id.recyclerViewVenue);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the venue list and adapter
        venueList = new ArrayList<>();
        adapter = new VenueAdapter(venueList);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
        ImageView addIconImageView = findViewById(R.id.addIconImageView1);
        addIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInterface.this, VenueRegistration.class);
                startActivity(intent);
            }
        });

        // Call the method to show venues in the layout
        showVenuesInRecyclerView();
    }
        @Override
        protected void onResume() {
            super.onResume();
            // Refresh the venue list when the activity resumes
            showVenuesInRecyclerView();
        }


    public List<venue> fetchAllVenues() {
        List<venue> venues = new ArrayList<>();
        database g = new database(UserInterface.this);
        SQLiteDatabase db = g.getReadableDatabase();
        Cursor cursor = db.query("venues", new String[]{"venue_name", "venue_location", "venue_price","image_url"},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract values from the cursor
                String name = cursor.getString(cursor.getColumnIndexOrThrow("venue_name"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("venue_location"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("venue_price"));
                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_url"));


                // Create a new venue object with default values for id and imageUrl
                venue venue = new venue(0, name, location, price,imageUrl);
                venues.add(venue);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return venues;
    }

    public void showVenuesInRecyclerView() {
        // Clear the existing venue list and fetch the latest data from the database
        List<venue> newVenues = fetchAllVenues();
        venueList.addAll(newVenues);
        adapter.updateData(newVenues);
    }
}





