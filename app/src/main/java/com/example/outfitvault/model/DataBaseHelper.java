package com.example.outfitvault.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String OUTFIT_TABLE = "OUTFIT_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_IMAGE_NAME = "IMAGE_NAME";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_SEASON = "SEASON";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "outfit.db", null, 1);
    }

    // Create table schema here
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + OUTFIT_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_SEASON + " TEXT)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    // called when version number changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
