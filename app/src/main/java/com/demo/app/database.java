package com.demo.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {
    public static final String dbname="eventsy.db";
    public database(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String q= "create table users(id integer primary key autoincrement, username text, password password, email email, phone TEXT CHECK (LENGTH(phone) = 10))";
        sqLiteDatabase.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        onCreate(sqLiteDatabase);
    }
    public boolean insert_data(String username, String password, String email, String phone){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c= new ContentValues();
        c.put("username", username);
        c.put("password", password);
        c.put("email", email);
        c.put("phone", phone);
        long r=db.insert("users", null ,c);
        if (r==-1) return false;
        else
            return true;
    }
}
