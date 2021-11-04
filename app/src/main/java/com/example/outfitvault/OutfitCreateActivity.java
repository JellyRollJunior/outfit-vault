package com.example.outfitvault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.types.Season;

public class OutfitCreateActivity extends AppCompatActivity {
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_create);

        populateSpinnerWithSeasons();
        wireFavoriteButton();
        wireSetImageButton();
    }

    private void populateSpinnerWithSeasons() {
        Spinner seasonSpinner = findViewById(R.id.outfitCreateSeasonSpinner);
        ArrayAdapter<Season> spinnerAdapter = new ArrayAdapter<Season>(
                OutfitCreateActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                Season.values()
        );
        seasonSpinner.setAdapter(spinnerAdapter);
    }

    private void wireFavoriteButton() {
        Button favoriteBtn = findViewById(R.id.outfitCreateSetFavoriteButton);
        favoriteBtn.setOnClickListener(view -> {
            isFavorite = !isFavorite;
        });
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
                Outfit newOutfit = compileOutfitDetails();
                if (addToDatabase(newOutfit)) {
                    Toast.makeText(OutfitCreateActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                }
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean addToDatabase(Outfit newOutfit) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(OutfitCreateActivity.this);
        return dataBaseHelper.addOne(newOutfit);
    }

    private Outfit compileOutfitDetails() {
        // TODO: get image name
        String imageName = "sample name";

        EditText editTextDescription = findViewById(R.id.outfitCreateDescriptionET);
        String description = editTextDescription.getText().toString();

        Spinner seasonSpinner = findViewById(R.id.outfitCreateSeasonSpinner);
        Season season = (Season) seasonSpinner.getSelectedItem();

        Outfit newOutfit = new Outfit(100, imageName, description, season, isFavorite);

        // debug
//        Toast.makeText(OutfitCreateActivity.this, newOutfit.toString(), Toast.LENGTH_LONG).show();
        return newOutfit;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, OutfitCreateActivity.class);
    }

    private void wireSetImageButton() {
        // TODO: move to camera -> start activity for result image name
        Button setImageButton = findViewById(R.id.outfitCreateSetImageButton);
        setImageButton.setOnClickListener(view -> {
            if (hasCameraPermission()) {
                enableCamera();
            } else {
                requestPermission();
            }
        });
    }

    private boolean hasCameraPermission() {
        // return bool if our camera permission == PERMISSION_GRANTED
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }

    private void enableCamera() {
        Intent intent = CameraActivity.makeIntent(OutfitCreateActivity.this);
        startActivity(intent);
    }
}