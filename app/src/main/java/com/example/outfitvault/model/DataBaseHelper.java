package com.example.outfitvault.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, "outfit.db", null, 1);
    }

    // called first time db is generated
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    // called when version number changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
