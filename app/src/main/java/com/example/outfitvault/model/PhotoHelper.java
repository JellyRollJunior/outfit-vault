package com.example.outfitvault.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.nfc.Tag;
import android.os.Environment;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.io.File;

public class PhotoHelper {

    private static final String TAG = "com.example.outfitvault.model.PhotoHelper";

    public static Bitmap rotate90Degrees(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static File getPhotoFilePath(Context context, String photoName) {
        String imageFilePathName = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + photoName;
        Log.d(TAG, "getPhotoFilePath: " + imageFilePathName);
        return new File(imageFilePathName);
    }

}
