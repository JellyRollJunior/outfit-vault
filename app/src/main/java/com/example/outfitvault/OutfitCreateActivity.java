package com.example.outfitvault;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.model.PhotoHelper;
import com.example.outfitvault.types.Season;

public class OutfitCreateActivity extends OutfitModifierAbstract {
    private final String TAG = "com.example.outfitvault.OutfitCreateActivity";
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    private Context context;
    private ImageView ivOutfit;
    private Button btnFavorite;
    private Spinner spnSeason;
    private Button btnTakePhoto;
    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_create);

        instantiateUI();

        // abstract methods
        wireFavoriteButton(btnFavorite);
        populateSpinner(context, spnSeason);
        wireSetTakePhoto(context, btnTakePhoto);

        // non abstract methods
    }

    @Override
    void instantiateUI() {
        context = OutfitCreateActivity.this;
        ivOutfit = findViewById(R.id.iv_outfit_create);
        btnFavorite = findViewById(R.id.btn_favorite_outfit_create);
        spnSeason = findViewById(R.id.spn_season_create);
        btnTakePhoto = findViewById(R.id.btn_take_photo_create);
        etDescription = findViewById(R.id.et_description_outfit_create);
    }

    @Override
    protected void onResume() {
        if (photoName != null) {
            Outfit tmpOutfit = compileOutfitDetails(999, etDescription, spnSeason);
            populateOutfitImageView(context, ivOutfit, tmpOutfit);
        }
        super.onResume();
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
            case R.id.outfit_menu_create:
                if (photoName != null) {
                    Outfit newOutfit = compileOutfitDetails(999, etDescription, spnSeason);
                    boolean insertSuccess = addToDatabase(newOutfit);

                    if (insertSuccess) {
                        Toast.makeText(OutfitCreateActivity.this, getString(R.string.successfully_added), Toast.LENGTH_SHORT)
                                .show();
                    }

                    Log.d(TAG, "onOptionsItemSelected: insert success? " + insertSuccess);
                    finish();
                    break;
                } else {
                    Toast.makeText(
                            OutfitCreateActivity.this,
                            "Take photo first!",
                            Toast.LENGTH_SHORT)
                         .show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private boolean addToDatabase(Outfit newOutfit) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(OutfitCreateActivity.this);
        return dataBaseHelper.addOne(newOutfit);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, OutfitCreateActivity.class);
    }
}