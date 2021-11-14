package com.example.outfitvault;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.outfitvault.model.Outfit;

public class OutfitCreateActivity extends OutfitModifierAbstract {
    private final String TAG = "com.example.outfitvault.OutfitCreateActivity";

    private Context context;
    private ImageView ivOutfit;
    private ImageButton ibtnFavorite;
    private Spinner spnSeason;
    private Button btnTakePhoto;
    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_create);

        context = OutfitCreateActivity.this;
        instantiateDatabase(context);
        instantiateViews();

        // abstract methods
        wireFavoriteButton(ibtnFavorite);
        populateSpinner(context, spnSeason);
        wireSetTakePhoto(context, btnTakePhoto);

        // non abstract methods
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
    void instantiateViews() {
        ivOutfit = findViewById(R.id.iv_outfit_create);
        ibtnFavorite = findViewById(R.id.btn_favorite_outfit_create);
        spnSeason = findViewById(R.id.spn_season_create);
        btnTakePhoto = findViewById(R.id.btn_take_photo_create);
        etDescription = findViewById(R.id.et_description_outfit_create);
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
                    boolean insertSuccess = dataBaseHelper.addOne(newOutfit);

                    if (insertSuccess) {
                        Toast.makeText(context, getString(R.string.successfully_added), Toast.LENGTH_SHORT)
                                .show();
                    }

                    Log.d(TAG, "onOptionsItemSelected: insert success? " + insertSuccess);
                    finish();
                    break;
                } else {
                    Toast.makeText(context, "Take photo first!", Toast.LENGTH_SHORT)
                            .show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, OutfitCreateActivity.class);
    }
}