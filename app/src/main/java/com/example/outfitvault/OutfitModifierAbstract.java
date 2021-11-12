package com.example.outfitvault;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.types.Season;

import java.io.File;

public abstract class OutfitModifierAbstract extends OutfitDisplayAbstract {

    private static String TAG = "com.example.outfitvault.OutfitModifierAbstract";
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    public String photoName;

    public void wireFavoriteButton(Button button) {
        button.setOnClickListener(view -> {
            isFavorite = !isFavorite;
        });
    }

    public void populateSpinner(Context context, Spinner spnSeason) {
        ArrayAdapter<Season> spinnerAdapter =
                    new ArrayAdapter<Season>(
                            context,
                            android.R.layout.simple_spinner_dropdown_item,
                            Season.values()
                    );
        spnSeason.setAdapter(spinnerAdapter);
    }

    public void wireSetTakePhoto(Context context, Button btnTakePhoto) {
        btnTakePhoto.setOnClickListener(view -> {
            if (hasCameraPermission()) {
                enableCamera(context, cameraActivityResultLauncher);
            } else {
                requestPermission();
            }
        });
    }

    private final ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
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

    private void enableCamera(Context context, ActivityResultLauncher<Intent> cameraActivityResultLauncher) {
        Intent intent = CameraActivity.makeIntent(context);
        cameraActivityResultLauncher.launch(intent);
    }

    private boolean hasCameraPermission() {
        // return bool if our camera permission == PERMISSION_GRANTED
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }

    public Outfit compileOutfitDetails(int outfitID, EditText etDescription, Spinner spnSeason) {
        String description = etDescription.getText().toString();
        Season season = (Season) spnSeason.getSelectedItem();

        Outfit newOutfit;
        if (photoName != null) {
            newOutfit = new Outfit(outfitID, photoName, description, season, isFavorite);
        } else {
            // this should never be reached:
            //      (OutfitCreateActivity) checks if photoName is null,
            //      (OutfitEditActivity) photoName by definition is not null
            newOutfit = new Outfit(-1, "ERROR", "ERROR", season, isFavorite);
        }

        // debug
        Log.d(TAG, "compileOutfitDetails: " + newOutfit.toString());
        return newOutfit;
    }
}
