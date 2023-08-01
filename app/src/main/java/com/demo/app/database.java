package com.demo.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.database.sqlite.SQLiteException;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {
    public static final String dbname = "eventsy.db";

    public database(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the users table
        String createUserTableQuery = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, email TEXT, phone TEXT CHECK (LENGTH(phone) = 10))";
        sqLiteDatabase.execSQL(createUserTableQuery);

        // Create the vendors table
        String createVendorTableQuery = "CREATE TABLE vendors (id INTEGER PRIMARY KEY AUTOINCREMENT, vendorname TEXT, organization TEXT, password Text, email TEXT, phone TEXT CHECK (LENGTH(phone) = 10))";
        sqLiteDatabase.execSQL(createVendorTableQuery);

        //Create the vendor's venue table
        String createVenueTableQuery = "CREATE TABLE venues (id INTEGER PRIMARY KEY AUTOINCREMENT, venue_name TEXT, venue_location TEXT, venue_price REAL, image_url TEXT)";
        sqLiteDatabase.execSQL(createVenueTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            sqLiteDatabase.beginTransaction();
            // Drop both tables on upgrade
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS vendors");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS venues");
            onCreate(sqLiteDatabase);
            sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLiteException e) {
            // Log the error message
            Log.e("DatabaseUpgrade", "Error occurred during database upgrade: " + e.getMessage());
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }


    public boolean insertUserData(String username, String password, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("username", username);
        c.put("password", password);
        c.put("email", email);
        c.put("phone", phone);
        long r = db.insert("users", null, c);
        db.close();
        return r != -1;
    }

    public boolean insertVendorData(String vname, String organization, String password, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("vendorName", vname);
        c.put("organization", organization);
        c.put("password", password);
        c.put("email", email);
        c.put("phone", phone);
        long r = db.insert("vendors", null, c);
        db.close();
        return r != -1;
    }

    public boolean insertVenueData(String venue_name, String venue_location, Double venue_price, String image_url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("venue_name", venue_name);
        c.put("venue_location", venue_location);
        c.put("venue_price", venue_price);
        c.put("image_url", image_url);

        Log.d("VenueRegistration", "Inserting venue data: Name=" + venue_name + ", Location=" + venue_location + ", Pricing=" + venue_price + ", Image URL=" + image_url);

        long r = db.insert("venues", null, c);
        db.close();
        return r != -1;
    }
    public List<venue> getAllVenues() {
        List<venue> venueList = new ArrayList<>();

        String query = "SELECT * FROM venues";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int venueId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String venueName = cursor.getString(cursor.getColumnIndexOrThrow("venue_name"));
                String venueLocation = cursor.getString(cursor.getColumnIndexOrThrow("venue_location"));
                double venuePrice = cursor.getDouble(cursor.getColumnIndexOrThrow("venue_price"));
                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_url"));

                venue venue = new venue(venueId, venueName, venueLocation, venuePrice, imageUrl);
                venueList.add(venue);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return venueList;
    }

}



