package com.example.outfitvault;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onResume() {
        refreshRecyclerView();
        super.onResume();
    }

    private void refreshRecyclerView() {
        instantiateDatabase();
        displayOnRecView(outfits);
    }

    private void wireAddOutfitFloatingActionButton() {
        FloatingActionButton fabAddButton = findViewById(R.id.fab_add_outfits);

        fabAddButton.setOnClickListener(view -> {
            Intent intent = OutfitCreateActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void instantiateDatabase() {
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        outfits = dataBaseHelper.getAll();

        // debug
        Log.d(TAG, "instantiateDatabase: " + outfits.toString());
    }

    private void displayOnRecView(List<Outfit> outfits) {
        RecyclerView rvDisplayOutfits = findViewById(R.id.rv_display_outfits);
        OutfitRecViewAdapter rvAdapter = new OutfitRecViewAdapter(MainActivity.this, outfits);

        rvDisplayOutfits.setAdapter(rvAdapter);
        rvDisplayOutfits.setLayoutManager(new GridLayoutManager(MainActivity.this, NUM_COLS));
    }

}

/*
    // SQLITE db delete debug
    Outfit outfit = new Outfit(1, "newIMAGE!", "it's a new image", Season.SPRING, false);
    boolean successDel = dataBaseHelper.deleteOne(outfit);
    if (successDel) {
        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
    }
 */
