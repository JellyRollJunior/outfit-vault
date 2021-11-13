package com.example.outfitvault;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;

import java.io.File;

public abstract class OutfitDisplayAbstract extends AppCompatActivity {

    private static String TAG = "com.example.outfitvault.OutfitDisplayAbstract";

    public DataBaseHelper dataBaseHelper;
    public boolean isFavorite = false;

    abstract void instantiateUI();

    public void instantiateDatabase(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(context);
        }
    }

    public void setFavoriteButtonVector(ImageButton imageButton) {
        if (isFavorite) {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_44);
        } else {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_border_44);
        }
    }

    public void populateOutfitImageView(Context context, ImageView imageView, Outfit outfit) {
        String photoFilePath = getPhotoFile(context, outfit.getPhotoName())
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
