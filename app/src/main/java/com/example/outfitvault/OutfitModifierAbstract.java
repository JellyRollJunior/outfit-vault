package com.example.outfitvault;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.model.PhotoHelper;

import java.io.File;

public abstract class OutfitModifierAbstract extends AppCompatActivity {

    private static String TAG = "com.example.outfitvault.OutfitDisplayAbstract";

    public String photoName;
    public DataBaseHelper dataBaseHelper;
    public boolean isFavorite = false;

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

    public void instantiateDatabase(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(context);
        }
    }

    public void wireFavoriteButton(Button button) {
        button.setOnClickListener(view -> {
            isFavorite = !isFavorite;
        });
    }

    public void populateOutfitImageView(Context context, ImageView imageView, Outfit outfit) {
        String photoFilePath = getPhotoFile(context, outfit.getImageName())
                                    .getAbsolutePath();
        Bitmap photoBitmap = BitmapFactory.decodeFile(photoFilePath);
        Bitmap rotatedBitmap = rotate90Degrees(photoBitmap);
        imageView.setImageBitmap(rotatedBitmap);
    }

    private File getPhotoFile(Context context, String photoName) {
        String imageFilePathName = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + photoName;
        Log.d(TAG, "getPhotoFilePath: " + imageFilePathName);
        return new File(imageFilePathName);
    }

    private Bitmap rotate90Degrees(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(
                        bitmap, 0, 0,
                        bitmap.getWidth(),
                        bitmap.getHeight(),
                        matrix, true);
    }
}
