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

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        List<Outfit> outfits = dataBaseHelper.getAll();
        Toast.makeText(MainActivity.this, outfits.toString(), Toast.LENGTH_LONG).show();

        Outfit outfit = new Outfit(1, "newIMAGE!", "it's a new image", Season.SPRING);
        dataBaseHelper.deleteOne(outfit);
        Toast.makeText(MainActivity.this, outfits.toString(), Toast.LENGTH_LONG).show();
    }
}
/*
    // Outfit debug
    Outfit outfit = new Outfit(100, "newIMAGE!", "it's a new image", Season.SPRING);
    Toast.makeText(MainActivity.this, outfit.toString(), Toast.LENGTH_LONG).show();

    // SQLITE db debug
    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
    boolean success = dataBaseHelper.addOne(outfit);
    if (success) {
        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
    }
 */