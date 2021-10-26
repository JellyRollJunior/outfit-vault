package com.example.outfitvault;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.types.Season;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // display database
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        List<Outfit> outfits = dataBaseHelper.getAll();
        Toast.makeText(MainActivity.this, outfits.toString(), Toast.LENGTH_LONG).show();


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
