package com.example.outfitvault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.outfitvault.model.Outfit;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class OutfitViewActivity extends OutfitDisplayAbstract {

    private static final String EXTRA_OUTFIT_ID = "com.example.outfitvault.OutfitViewActivity - outfitID";
    private static final String TAG = "com.example.outfitvault.OutfitViewActivity";
    private Outfit currentOutfit;
    private Context context;

    private ImageView ivOutfit;
    private Button btnFavorite;
    private TextView tvSeason;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_view);

        // debug
        Log.d(TAG, "onCreate: Outfit ID: " + getExtraOutfitID());

        // abstract methods
        instantiateVariables();
        instantiateUI();
        populateOutfitImageView(context, ivOutfit, currentOutfit);

        // non abstract methods
        populateTextViews();
        wireFavoriteButton();
        wireEditButton();
        wireDeleteButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCardView();
    }

    private void refreshCardView() {
        currentOutfit = dataBaseHelper.getOutfitFromID(currentOutfit.getID());
        populateOutfitImageView(context, ivOutfit, currentOutfit);
        populateTextViews();
    }

    private void instantiateVariables() {
        context = OutfitViewActivity.this;
        instantiateDatabase(context);

        int currentOutfitID = getExtraOutfitID();
        currentOutfit = dataBaseHelper.getOutfitFromID(currentOutfitID);

        isFavorite = currentOutfit.getFavorite();
    }

    @Override
    void instantiateUI() {
        ivOutfit = findViewById(R.id.iv_outfit_view);
        btnFavorite = findViewById(R.id.btn_favorite_outfit_view);
        tvSeason = findViewById(R.id.tv_season);
        tvDescription = findViewById(R.id.tv_description_outfit_view);
    }

    private void populateTextViews() {
        tvSeason.setText(currentOutfit.getSeason().toString());
        tvDescription.setText(currentOutfit.getDescription());
    }

    private void wireFavoriteButton() {
        btnFavorite.setOnClickListener(view -> {
            isFavorite = !isFavorite;

            currentOutfit.setFavorite(isFavorite);
            boolean updateSuccess = dataBaseHelper.update(currentOutfit.getID(), currentOutfit);

            //debug
            Log.d(TAG, "wireFavoriteButton: update success: " + updateSuccess);

            // TODO: change UI to reflect favorite status (maybe put in abstract)
        });
    }

    private void wireEditButton() {
        Button btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(view -> {
            Intent intent = OutfitEditActivity.makeIntent(context, currentOutfit);
            startActivity(intent);
        });
    }

    private void wireDeleteButton() {
        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(view -> {
            new MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.delete_outfit)
                    .setMessage(R.string.delete_outfit_confirmation)
                    .setNegativeButton(R.string.cancel, (dialogInterface, i) -> { /* do nothing*/ } )
                    .setPositiveButton(R.string.delete, (dialogInterface, i) -> deleteOutfit())
                    .show();
        });
    }

    private void deleteOutfit() {
        boolean deleteSuccess = dataBaseHelper.deleteOne(currentOutfit.getID());
        if (deleteSuccess){
            Toast.makeText(context, R.string.successful_delete, Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(context, R.string.error_deleting_outfit, Toast.LENGTH_SHORT)
                    .show();
        }

        finish();
    }

    private int getExtraOutfitID() {
        Intent i = getIntent();
        return i.getIntExtra(EXTRA_OUTFIT_ID, -1);
    }

    public static Intent makeIntent(Context context, Outfit outfit) {
        Intent intent = new Intent(context, OutfitViewActivity.class);
        intent.putExtra(EXTRA_OUTFIT_ID, outfit.getID());
        return intent;
    }


}