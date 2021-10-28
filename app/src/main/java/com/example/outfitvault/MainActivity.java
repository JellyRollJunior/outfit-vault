package com.example.outfitvault;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int NUM_COLS = 3;
    private List<Outfit> outfits = new ArrayList<>();
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instantiateData();
        displayOnRecView(outfits);
    }

    private void instantiateData() {
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        outfits = dataBaseHelper.getAll();

        // debug
        Toast.makeText(MainActivity.this, outfits.toString(), Toast.LENGTH_LONG).show();
    }

    private void displayOnRecView(List<Outfit> outfits) {
        RecyclerView rvDisplayOutfits = findViewById(R.id.rvDisplayOutfits);
        OutfitRecViewAdapter rvAdapter = new OutfitRecViewAdapter(MainActivity.this, outfits);
        rvDisplayOutfits.setAdapter(rvAdapter);
        rvDisplayOutfits.setLayoutManager(new GridLayoutManager(MainActivity.this, NUM_COLS));
    }

}

/*
    // Outfit debug
    Outfit outfit = new Outfit(100, "newIMAGE!", "it's a new image", Season.SPRING);
    Toast.makeText(MainActivity.this, outfit.toString(), Toast.LENGTH_LONG).show();

    // SQLITE db add debug
    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
    boolean successAdd = dataBaseHelper.addOne(outfit);
    if (successAdd) {
        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
    }

    // SQLITE db delete debug
    Outfit outfit = new Outfit(1, "newIMAGE!", "it's a new image", Season.SPRING);
    boolean successDel = dataBaseHelper.deleteOne(outfit);
    if (successDel) {
        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
    }

    // SQLITE db getALL debug
    List<Outfit> outfits = dataBaseHelper.getAll();
    Toast.makeText(MainActivity.this, outfits.toString(), Toast.LENGTH_LONG).show();
 */
