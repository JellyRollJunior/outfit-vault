package com.example.outfitvault;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class OutfitEditActivity extends AppCompatActivity {

    public static final String EXTRA_OUTFIT_ID_EDIT = "com.example.outfitvault.OutfitEditActivity - outfit ID";
    public static final String TAG = "com.example.outfitvault.OutfitEditActivity";
    private Outfit currentOutfit;
    private DataBaseHelper dataBaseHelper;
    private boolean isFavorite;
    private String photoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_edit);

        // instantiate variables
        dataBaseHelper = new DataBaseHelper(OutfitEditActivity.this);
        currentOutfit = dataBaseHelper.getOutfitFromID(getExtraOutfitID());
        isFavorite = currentOutfit.getFavorite();
        photoName = currentOutfit.getImageName();

        populateSpinner();
        populateEditText();

        ImageView ivOutfit = findViewById(R.id.iv_outfit_edit);
        PhotoHelper.populateImageViewWithOutfit(
                        OutfitEditActivity.this,
                        ivOutfit,
                        currentOutfit);

        wireFavoriteButton();
        wireSetTakePhoto();
    }

    @Override
    protected void onResume() {
        ImageView ivOutfit = findViewById(R.id.iv_outfit_edit);
        Outfit tmpOutfit = compileOutfitDetails();
        PhotoHelper.populateImageViewWithOutfit(
                OutfitEditActivity.this,
                ivOutfit,
                tmpOutfit);
        super.onResume();
    }

    private void wireSetTakePhoto() {
        ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        if (data != null) {
                            photoName = CameraActivity.getImageName(data);

                            // debug
                            Log.d(TAG, "from camera: imageName is " + photoName);
                        }
                    } else {
                        Log.d(TAG, "onCreate: " + result.toString());
                    }
                }
        );

        Button btnSetImage = findViewById(R.id.btn_take_photo_edit);
        btnSetImage.setOnClickListener(view -> {
            enableCamera(cameraActivityResultLauncher);
        });
    }

    private void enableCamera(ActivityResultLauncher<Intent> cameraActivityResultLauncher) {
        Intent intent = CameraActivity.makeIntent(OutfitEditActivity.this);
        cameraActivityResultLauncher.launch(intent);
    }

    private void wireFavoriteButton() {
        Button btnFavorite = findViewById(R.id.btn_favorite_outfit_edit);
        btnFavorite.setOnClickListener(view -> {
            isFavorite = !isFavorite;
        });
    }

    private void populateEditText() {
        EditText etDescription = findViewById(R.id.et_description_outfit_edit);
        etDescription.setText(currentOutfit.getDescription());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_outfit_edit, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.outfit_menu_edit:
                Outfit outfit = compileOutfitDetails();
                boolean updateSuccess = dataBaseHelper.update(getExtraOutfitID(), outfit);
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

    private Outfit compileOutfitDetails() {
        EditText etDescription = findViewById(R.id.et_description_outfit_edit);
        String description = etDescription.getText().toString();

        Spinner spnSeason = findViewById(R.id.spn_season_edit);
        Season season = (Season) spnSeason.getSelectedItem();

        Outfit newOutfit = new Outfit(getExtraOutfitID(), photoName, description, season, isFavorite);

        // debug
        Log.d(TAG, "compileOutfitDetails: " + newOutfit.toString());
        return newOutfit;
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