package com.example.outfitvault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.types.Season;

public class OutfitEditActivity extends AppCompatActivity {

    public static final String EXTRA_OUTFIT_ID_EDIT = "com.example.outfitvault.OutfitEditActivity - outfit ID";
    private Outfit currentOutfit;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_edit);

        dataBaseHelper = new DataBaseHelper(OutfitEditActivity.this);
        currentOutfit = dataBaseHelper.getOutfitFromID(getExtraOutfitID());

        populateSpinner();
    }


    private void populateSpinner() {
        Spinner spnSeason = findViewById(R.id.spn_season_edit);
        ArrayAdapter<Season> spinnerAdapter =
                new ArrayAdapter<Season>(
                        OutfitEditActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        Season.values()
                );
        spnSeason.setAdapter(spinnerAdapter);

        // set default based on outfit
        int i = 0;
        for (Season season: Season.values()) {
            if (currentOutfit.getSeason() == season) {
                spnSeason.setSelection(i);
            }
            i++;
        }
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