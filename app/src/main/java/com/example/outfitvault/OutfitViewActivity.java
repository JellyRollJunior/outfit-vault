package com.example.outfitvault;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.model.PhotoHelper;

public class OutfitViewActivity extends AppCompatActivity {

    private static final String OUTFIT_ID = "com.example.outfitvault.OutfitViewActivity - outfitID";
    private Outfit currentOutfit;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_view);

        instantiateVariables();
        populateUIWithOutfitDetails();

        // debug
        Toast.makeText(OutfitViewActivity.this, "THE NUMBER IS: " + getExtraCurrentOutfitID(), Toast.LENGTH_LONG).show();
    }

    private void populateUIWithOutfitDetails() {
        // set image with image name
        ImageView ivOutfit = findViewById(R.id.outfitViewIV);
        String photoFilePath = PhotoHelper.getPhotoFile(OutfitViewActivity.this, currentOutfit.getImageName()).getAbsolutePath();
        Bitmap photoBitmap = BitmapFactory.decodeFile(photoFilePath);

        Bitmap rotatedBitmap = PhotoHelper.rotate90Degrees(photoBitmap);
        ivOutfit.setImageBitmap(rotatedBitmap);

        // set favorite button style with favorite

        TextView tvSeason = findViewById(R.id.tvSeason);
        tvSeason.setText(currentOutfit.getSeason().toString());

        TextView tvDescription = findViewById(R.id.outfitCreateDescriptionET);
        tvDescription.setText(currentOutfit.getDescription());
    }

    private void instantiateVariables() {
        dataBaseHelper = new DataBaseHelper(OutfitViewActivity.this);
        int currentOutfitID = getExtraCurrentOutfitID();
        currentOutfit = dataBaseHelper.getOutfitFromID(currentOutfitID);
    }

    private int getExtraCurrentOutfitID() {
        Intent i = getIntent();
        return i.getIntExtra(OUTFIT_ID, -1);
    }

    public static Intent makeIntent(Context context, int outfitID) {
        Intent intent = new Intent(context, OutfitViewActivity.class);
        intent.putExtra(OUTFIT_ID, outfitID);
        return intent;
    }
}