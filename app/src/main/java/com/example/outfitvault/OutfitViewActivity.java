package com.example.outfitvault;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.model.PhotoHelper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class OutfitViewActivity extends AppCompatActivity {

    private static final String EXTRA_OUTFIT_ID = "com.example.outfitvault.OutfitViewActivity - outfitID";
    private static final String TAG = "com.example.outfitvault.OutfitViewActivity";
    private Outfit currentOutfit;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_view);

        instantiateVariables();
        populateOutfitUI();
        wireDeleteButton();

        // debug
        Log.d(TAG, "onCreate: Outfit ID: " + getExtraCurrentOutfitID());
    }

    private void wireDeleteButton() {
        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(view -> {
            new MaterialAlertDialogBuilder(OutfitViewActivity.this)
                        .setTitle(R.string.delete_outfit)
                        .setMessage(R.string.delete_outfit_confirmation)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // do nothing
                            }
                        })
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dataBaseHelper = new DataBaseHelper(OutfitViewActivity.this);
                                dataBaseHelper.deleteOne(currentOutfit.getID());
                                finish();
                            }
                        })
                        .show();
        });
    }

    private void populateOutfitUI() {
        ImageView ivOutfit = findViewById(R.id.iv_outfit_view);
        String photoFilePath = PhotoHelper.getPhotoFile(OutfitViewActivity.this, currentOutfit.getImageName()).getAbsolutePath();
        Bitmap photoBitmap = BitmapFactory.decodeFile(photoFilePath);
        Bitmap rotatedBitmap = PhotoHelper.rotate90Degrees(photoBitmap);
        ivOutfit.setImageBitmap(rotatedBitmap);

        TextView tvSeason = findViewById(R.id.tv_season);
        tvSeason.setText(currentOutfit.getSeason().toString());

        TextView tvDescription = findViewById(R.id.et_description_outfit_view);
        tvDescription.setText(currentOutfit.getDescription());
    }

    private void instantiateVariables() {
        int currentOutfitID = getExtraCurrentOutfitID();

        dataBaseHelper = new DataBaseHelper(OutfitViewActivity.this);
        currentOutfit = dataBaseHelper.getOutfitFromID(currentOutfitID);
    }

    private int getExtraCurrentOutfitID() {
        Intent i = getIntent();
        return i.getIntExtra(EXTRA_OUTFIT_ID, -1);
    }

    public static Intent makeIntent(Context context, int outfitID) {
        Intent intent = new Intent(context, OutfitViewActivity.class);
        intent.putExtra(EXTRA_OUTFIT_ID, outfitID);
        return intent;
    }
}