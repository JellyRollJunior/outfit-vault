package com.example.outfitvault;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.types.Season;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Outfit debug
        Outfit outfit = new Outfit(100, "newIMAGE!", "it's a new image", Season.SPRING);
        //Toast.makeText(MainActivity.this, outfit.toString(), Toast.LENGTH_LONG).show();

        // SQLITE db debug
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
//        boolean success = dataBaseHelper.addOne(outfit);
//        if (success) {
//            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
//        }

        List<Outfit> outfits = dataBaseHelper.getAll();
        Toast.makeText(MainActivity.this, outfits.toString(), Toast.LENGTH_LONG).show();
    }
}