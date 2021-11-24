package com.example.outfitvault;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.model.TextHelper;
import com.example.outfitvault.types.Season;

import java.util.Objects;

public class OutfitEditActivity extends OutfitModifierAbstract {

    public static final String EXTRA_OUTFIT_ID_EDIT = "com.example.outfitvault.OutfitEditActivity - outfit ID";
    public static final String TAG = "com.example.outfitvault.OutfitEditActivity";

    private Context context;
    private int currentOutfitID;
    private Outfit currentOutfit;
    private ImageView ivOutfit;
    private ImageButton ibtnFavorite;
    private Spinner spnSeason;
    private Button btnTakePhoto;
    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_edit);

        instantiateVariables();
        instantiateViews();

        // abstract methods
        populateOutfitImageView(context, ivOutfit, currentOutfit);
        wireFavoriteButton(ibtnFavorite);
        populateSpinner(context, spnSeason);
        wireSetTakePhoto(context, btnTakePhoto);

        // non abstract methods
        setDefaultSpinnerSelection();
        etDescription.setText(currentOutfit.getDescription());

        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        // refresh imageView with new Photo
        Outfit tmpOutfit = compileOutfitDetails(currentOutfitID, etDescription, spnSeason);
        populateOutfitImageView(context, ivOutfit, tmpOutfit);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        currentOutfit = dataBaseHelper.getOutfitFromID(currentOutfitID);
        removePhotoFromList(photoList, currentOutfit.getPhotoName());
        deleteUnusedPhotos(context);
        super.onDestroy();
    }

    private void instantiateVariables() {
        context = OutfitEditActivity.this;
        instantiateDatabase(context);
        currentOutfitID = getExtraOutfitID();
        currentOutfit = dataBaseHelper.getOutfitFromID(currentOutfitID);
        isFavorite = currentOutfit.getFavorite();
        outfitPhotoName = currentOutfit.getPhotoName();

        photoList.add(outfitPhotoName);
    }

    @Override
    void instantiateViews() {
        ivOutfit = findViewById(R.id.iv_outfit_create);
        ibtnFavorite = findViewById(R.id.btn_favorite_outfit_create);
        spnSeason = findViewById(R.id.spn_season_create);
        btnTakePhoto = findViewById(R.id.btn_take_photo_create);
        etDescription = findViewById(R.id.et_description_outfit_create);
    }

    private void setDefaultSpinnerSelection() {
        int i = 0;
        for (Season season: Season.values()) {
            if (currentOutfit.getSeason() == season) {
                spnSeason.setSelection(i);
            }
            i++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_outfit_edit, menu);

        // make menu text white
        MenuItem menuItemEdit = menu.getItem(0);
        menuItemEdit.setTitle(TextHelper
                .stringColorToWhite(menuItemEdit.getTitle())
        );

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.outfit_menu_edit:
                Outfit newOutfit = compileOutfitDetails(currentOutfitID, etDescription, spnSeason);
                boolean updateSuccess = dataBaseHelper.update(newOutfit);
                if (updateSuccess) {
                        Toast.makeText(context, getString(R.string.suuccessful_update), Toast.LENGTH_SHORT)
                                .show();
                }
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