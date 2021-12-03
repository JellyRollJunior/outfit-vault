package com.example.outfitvault;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.outfitvault.model.DataBaseHelper;
import com.example.outfitvault.model.Outfit;
import com.example.outfitvault.model.PhotoHelper;

/**
 * Provides methods supporting displaying outfit details.
 *
 * Users: OutfitViewActivity, OutfitEditActivity, OutfitCreateActivity.
 * */
public abstract class OutfitDisplayAbstract extends AppCompatActivity {

    private static String TAG = "com.example.outfitvault.OutfitDisplayAbstract";

    public DataBaseHelper dataBaseHelper;
    public boolean isFavorite = false;

    abstract void instantiateViews();

    public void instantiateDatabase(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(context);
        }
    }

    /** Changes favorite button image vector depending on outfit favorite status. */
    public void setFavoriteButtonVector(ImageButton imageButton) {
        if (isFavorite) {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_44);
        } else {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_border_44);
        }
    }

    /** Populates image view with outfit photo. */
    public void populateOutfitImageView(Context context, ImageView imageView, Outfit outfit) {
        String photoFilePath = PhotoHelper.getPhotoFile(context, outfit.getPhotoName())
                .getAbsolutePath();
        Bitmap photoBitmap = BitmapFactory.decodeFile(photoFilePath);
        Bitmap rotatedBitmap = PhotoHelper.rotate90Degrees(photoBitmap);
        imageView.setImageBitmap(rotatedBitmap);
    }
}
