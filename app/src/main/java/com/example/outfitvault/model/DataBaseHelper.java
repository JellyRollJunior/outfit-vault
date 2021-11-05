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
    private static final String COLUMN_FAVORITE = "FAVORITE" ;
    private final String TAG = "com.example.outfitvault.model.DatabaseHelper";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "outfit.db", null, 1);
    }

    // Create table schema here
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // by using AUTOINCREMENT, any value passed to columnID is useless as it gets overwritten
        String createTableStatement =
                    "CREATE TABLE " + OUTFIT_TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IMAGE_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_SEASON + " TEXT, " +
                    COLUMN_FAVORITE + " BOOL)";

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
        cv.put(COLUMN_FAVORITE, outfit.getFavorite());

        long insert = db.insert(OUTFIT_TABLE, null, cv);
        return insert != -1;
    }

    public boolean deleteOne(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString =
                    "DELETE FROM " + OUTFIT_TABLE + " " +
                    "WHERE " + COLUMN_ID + " = " + ID;

        Cursor cursor = db.rawQuery(queryString, null);

        // rawQuery returns deleted item. if deleted item is present in cursor, success!
        boolean deleteSuccess = cursor.moveToFirst();
        cursor.close();
        return deleteSuccess;
    }

    public Outfit getOutfitFromID(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString =
                    "SELECT * " +
                    "FROM " + OUTFIT_TABLE + " " +
                    "WHERE " + COLUMN_ID + " = " + ID;

        Cursor cursor = db.rawQuery(queryString, null);
        List<Outfit> tempOutfitList = cursorToList(cursor);
        cursor.close();

        if (tempOutfitList.size() == 0) {
            return null;
        } else {
            return tempOutfitList.get(0);
        }
    }

    public List<Outfit> getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString =
                    "SELECT * " +
                    "FROM " + OUTFIT_TABLE + " " +
                    "ORDER BY " +
                        "CASE " + COLUMN_SEASON + " " +
                            "WHEN '" + Season.FALL.toString() + "' THEN 0 " +
                            "WHEN '" + Season.WINTER.toString() + "' THEN 1 " +
                            "WHEN '" + Season.SPRING.toString() + "' THEN 2 " +
                            "WHEN '" + Season.SUMMER.toString() + "' THEN 3 " +
                        "END ";

        Cursor cursor = db.rawQuery(queryString, null);
        List<Outfit> returnList = cursorToList(cursor);

        cursor.close();
        db.close();
        return returnList;
    }

    private List<Outfit> cursorToList(Cursor cursor) {
        List<Outfit> returnList = new ArrayList<>();
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
                // db saves boolean as 1 = true, 0 = false
                Boolean favorite = cursor.getInt(4) == 1 ? true: false;
                Outfit outfit = new Outfit(ID, imageName, description, season, favorite);
                returnList.add(outfit);
            } while (cursor.moveToNext());
        } // else return empty list

        cursor.close();
        return returnList;
    }
}
