package com.example.outfitvault.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.outfitvault.types.Season;

import java.util.ArrayList;
import java.util.List;

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
        // by using AUTOINCREMENT, any value passed to columnID is useless as it gets overwritten
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

    public boolean addOne(Outfit outfit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_IMAGE_NAME, outfit.getImageName());
        cv.put(COLUMN_DESCRIPTION, outfit.getDescription());
        cv.put(COLUMN_SEASON, outfit.getSeason().toString());

        long insert = db.insert(OUTFIT_TABLE, null, cv);
        return insert != -1;
    }

    public boolean deleteOne(Outfit outfit) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + OUTFIT_TABLE +
                " WHERE " + COLUMN_ID + " = " + outfit.getID();

        Cursor cursor = db.rawQuery(queryString, null);

        // rawQuery returns deleted item. if deleted item is present in cursor, success!
        return cursor.moveToFirst();
    }

    public List<Outfit> getAll() {
        List<Outfit> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT * FROM " + OUTFIT_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                // extract values from tuple
                int ID = cursor.getInt(0);
                String imageName = cursor.getString(1);
                String description = cursor.getString(2);
                Season season;
                switch(cursor.getString(3)){
                    case "FALL":
                        season = Season.FALL;
                        break;
                    case "WINTER":
                        season = Season.WINTER;
                        break;
                    case "SPRING":
                        season = Season.SPRING;
                        break;
                    case "SUMMER":
                        season = Season.SUMMER;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + cursor.getString(3));
                }

                Outfit outfit = new Outfit(ID, imageName, description, season);
                returnList.add(outfit);
            } while (cursor.moveToNext());
        } // else return empty list

        cursor.close();
        db.close();
        return returnList;
    }
}
