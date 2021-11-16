package com.example.outfitvault.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

public class PhotoHelper {

    private static final String TAG = "com.example.outfitvault.model.PhotoHelper";

    public static Bitmap rotate90Degrees(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static File getPhotoFile(Context context, String photoName) {
        String imageFilePathName = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + photoName;
        Log.d(TAG, "getPhotoFilePath: " + imageFilePathName);
        return new File(imageFilePathName);
    }

}
