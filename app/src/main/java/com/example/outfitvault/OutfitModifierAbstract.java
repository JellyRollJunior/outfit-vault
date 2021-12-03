package com.example.outfitvault;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.model.PhotoHelper;
import com.example.outfitvault.types.Season;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides base for activities which modify outfits.
 * Implements methods which support creation / editing of outfits.
 *
 * Users: OutfitCreateActivity, OutfitEditActivity.
 * */
public abstract class OutfitModifierAbstract extends OutfitDisplayAbstract {

    private static String TAG = "com.example.outfitvault.OutfitModifierAbstract";
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    public List<String> photoList = new ArrayList<>();
    public String outfitPhotoName;

    public void wireFavoriteButton(ImageButton imageButton) {
        setFavoriteButtonVector(imageButton);
        imageButton.setOnClickListener(view -> {
            isFavorite = !isFavorite;
            setFavoriteButtonVector(imageButton);
        });
    }

    /** Populate spinner with Seasons. */
    public void populateSpinner(Context context, Spinner spnSeason) {
        ArrayAdapter<Season> spinnerAdapter =
                new ArrayAdapter<>(
                        context,
                        android.R.layout.simple_spinner_dropdown_item,
                        Season.values()
                );
        spnSeason.setPopupBackgroundResource(R.drawable.spinner_popup_background);
        spnSeason.setAdapter(spinnerAdapter);
    }

    /** Move to CameraActivity on click in order to take photo. */
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
                        outfitPhotoName = CameraActivity.getPhotoName(data);
                        photoList.add(outfitPhotoName);

                        // debug
                        Log.d(TAG, "from camera: imageName is " + outfitPhotoName);
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

    /** Returns an outfit by compiling data in views. */
    public Outfit compileOutfitDetails(int outfitID, EditText etDescription, Spinner spnSeason) {
        String description = etDescription.getText().toString();
        Season season = (Season) spnSeason.getSelectedItem();

        Outfit newOutfit;
        if (outfitPhotoName != null) {
            newOutfit = new Outfit(outfitID, outfitPhotoName, description, season, isFavorite);
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

    /** Deletes unused photos taken by user. */
    public void deleteUnusedPhotos(Context context) {
        for (String photo: photoList) {
            File deletePhoto = PhotoHelper.getPhotoFile(context, photo);
            boolean deleteSuccess = deletePhoto.delete();

            if (deleteSuccess) {
                Log.d(TAG, "deleteUnusedPhotos: DELETED PHOTO: " + photo);
            }
        }
    }

    /** Removes a photo from garbage collection list. */
    public void removePhotoFromList(List<String> photoList, String outfitName) {
        int i = 0;
        for (String photo : new ArrayList<String>(photoList)) {
            if (outfitName.equals(photo)) {
                photoList.remove(i);

                // debug
                Log.d(TAG, "popped photo from garbage collection: " + photo);
            }
            i++;
        }
    }

}
