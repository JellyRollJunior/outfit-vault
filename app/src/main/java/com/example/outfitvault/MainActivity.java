package com.example.outfitvault;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int NUM_COLS = 3;
    private final String TAG = "com.example.outfitvault.MainActivity";
    private List<Outfit> outfits = new ArrayList<>();
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // NEVER DELETE THIS LINE BY ACCIDENT OR CANT ACCESS VIEWS

        instantiateDatabase();
        displayOnRecView(outfits);
        wireAddOutfitFloatingActionButton();
    }

    private void wireAddOutfitFloatingActionButton() {
        FloatingActionButton fabAddButton = findViewById(R.id.fabAddOutfit);
        fabAddButton.setOnClickListener(view -> {
            Intent intent = OutfitCreateActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void instantiateDatabase() {
        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        // debug
//        Outfit outfit = new Outfit(100, "newIMAGE!", "it's a new image a\na\na\na\na\na\na\na\na\na\na\n", Season.SPRING, false);
//        Outfit outfit2 = new Outfit(200, "yup new image", "omg its a new image a\na\na\na\na\na\na\na\na\na\na\n", Season.FALL, true);
//        dataBaseHelper.addOne(outfit);
//        dataBaseHelper.addOne(outfit2);

        outfits = dataBaseHelper.getAll();

        // debug
        Toast.makeText(MainActivity.this, outfits.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(MainActivity.this, dataBaseHelper.getOutfitFromID(1).toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "instantiateDatabase: " + outfits.toString());
    }

    private void displayOnRecView(List<Outfit> outfits) {
        RecyclerView rvDisplayOutfits = findViewById(R.id.rvDisplayOutfits);
        OutfitRecViewAdapter rvAdapter = new OutfitRecViewAdapter(MainActivity.this, outfits);
        rvDisplayOutfits.setAdapter(rvAdapter);
        rvDisplayOutfits.setLayoutManager(new GridLayoutManager(MainActivity.this, NUM_COLS));
    }

}

/*
    // SQLITE db add debug
    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
    boolean successAdd = dataBaseHelper.addOne(outfit);
    if (successAdd) {
        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
    }

    // sample data
    Outfit outfit = new Outfit(100, "newIMAGE!", "it's a new image", Season.SPRING, false);
    Outfit outfit2 = new Outfit(200, "yup new image", "omg its a new image", Season.FALL, true);
    dataBaseHelper.addOne(outfit);
    dataBaseHelper.addOne(outfit2);

    // SQLITE db delete debug
    Outfit outfit = new Outfit(1, "newIMAGE!", "it's a new image", Season.SPRING, false);
    boolean successDel = dataBaseHelper.deleteOne(outfit);
    if (successDel) {
        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
    }

    // SQLITE db getALL debug
    List<Outfit> outfits = dataBaseHelper.getAll();
    Toast.makeText(MainActivity.this, outfits.toString(), Toast.LENGTH_LONG).show();
 */
