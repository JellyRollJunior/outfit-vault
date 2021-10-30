package com.example.outfitvault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.types.Season;

import java.lang.reflect.Array;

public class OutfitCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_create);

        // ID - IMAGENAME - DESCRIPTION - SEASON - FAVORITE
        Spinner seasonSpinner = findViewById(R.id.outfitCreateSeasonSpinner);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(
                OutfitCreateActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                Season.values()
        );
        seasonSpinner.setAdapter(spinnerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_outfit_create, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.outfitCreateMenuCreate:
                Toast.makeText(OutfitCreateActivity.this, "im working guys", Toast.LENGTH_SHORT).show();
                compileOutfitDetails();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void compileOutfitDetails() {
        // create and return outfit
        // Outfit newOutfit = new Outfit()
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, OutfitCreateActivity.class);
    }
}