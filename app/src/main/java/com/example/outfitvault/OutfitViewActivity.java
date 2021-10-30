package com.example.outfitvault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;

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

//         Button btn = findViewById(R.id.btnEditOutfit);
//         btn.setVisibility(View.GONE);
    }

    private void populateUIWithOutfitDetails() {
        // set image with image name


        // set favorite button style with favorite

        TextView tvSeason = findViewById(R.id.tvSeason);
        tvSeason.setText(currentOutfit.getSeason().toString());

        TextView tvDescription = findViewById(R.id.outfitAddDescriptionET);
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