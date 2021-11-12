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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.types.Season;

public class OutfitEditActivity extends OutfitModifierAbstract {

    public static final String EXTRA_OUTFIT_ID_EDIT = "com.example.outfitvault.OutfitEditActivity - outfit ID";
    public static final String TAG = "com.example.outfitvault.OutfitEditActivity";

    private int currentOutfitID;
    private Outfit currentOutfit;
    private ImageView ivOutfit;
    private Button btnFavorite;
    private Spinner spnSeason;
    private Button btnTakePhoto;
    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_edit);

        instantiateVariables();
        instantiateUI();

        populateOutfitImageView(OutfitEditActivity.this, ivOutfit, currentOutfit);
        wireFavoriteButton(btnFavorite);
        populateSpinner(OutfitEditActivity.this, spnSeason);
        setDefaultSpinner();
        wireSetTakePhoto(OutfitEditActivity.this, btnTakePhoto);

        etDescription.setText(currentOutfit.getDescription());
    }

    @Override
    protected void onResume() {
        ImageView ivOutfit = findViewById(R.id.iv_outfit_edit);
        Outfit tmpOutfit = compileOutfitDetails(currentOutfitID,etDescription, spnSeason);
        populateOutfitImageView(OutfitEditActivity.this, ivOutfit, tmpOutfit);
        super.onResume();
    }

    private void instantiateVariables() {
        instantiateDatabase(OutfitEditActivity.this);
        currentOutfitID = getExtraOutfitID();
        currentOutfit = dataBaseHelper.getOutfitFromID(currentOutfitID);
        isFavorite = currentOutfit.getFavorite();
        photoName = currentOutfit.getImageName();
    }

    @Override
    void instantiateUI() {
        ivOutfit = findViewById(R.id.iv_outfit_edit);
        btnFavorite = findViewById(R.id.btn_favorite_outfit_edit);
        spnSeason = findViewById(R.id.spn_season_edit);
        btnTakePhoto = findViewById(R.id.btn_take_photo_edit);
        etDescription = findViewById(R.id.et_description_outfit_edit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_outfit_edit, menu);
        return true;
    }

    private void setDefaultSpinner() {
        Spinner spnSeason = findViewById(R.id.spn_season_edit);
        int i = 0;
        for (Season season: Season.values()) {
            if (currentOutfit.getSeason() == season) {
                spnSeason.setSelection(i);
            }
            i++;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.outfit_menu_edit:
                Outfit outfit = compileOutfitDetails(currentOutfitID, etDescription, spnSeason);
                boolean updateSuccess = dataBaseHelper.update(currentOutfitID, outfit);
                if (updateSuccess) {
                        Toast.makeText(
                                OutfitEditActivity.this,
                                getString(R.string.suuccessful_update),
                                Toast.LENGTH_SHORT)
                                .show();
                }
                Log.d(TAG, "onOptionsItemSelected: " + updateSuccess);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private int getExtraOutfitID() {
        Intent i = getIntent();
        return i.getIntExtra(EXTRA_OUTFIT_ID_EDIT, -1);
    }

    public static Intent makeIntent(Context context, Outfit outfit) {
        Intent intent = new Intent(context, OutfitEditActivity.class);
        intent.putExtra(EXTRA_OUTFIT_ID_EDIT, outfit.getID());
        return intent;
    }
}