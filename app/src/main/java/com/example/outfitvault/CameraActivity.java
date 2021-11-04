package com.example.outfitvault;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity implements ImageAnalysis.Analyzer {
    public static final String EXTRA_IMAGE_NAME = "com.example.outfitvault.CameraActivity - imageName";
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        startCamera();
        wireTakePhotoButton();
    }

    private void wireTakePhotoButton() {
        Button takePhotoButton = findViewById(R.id.camera_capture_button);
        takePhotoButton.setOnClickListener(view -> {
            takePhoto();
        });
    }

    private void takePhoto() {
        String photoName = "" + System.currentTimeMillis() + ".jpg";
        File photoFilePath = getPhotoFilePath(CameraActivity.this, photoName);

        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(photoFilePath).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
//                        Toast.makeText(CameraActivity.this, "Image has been saved successfully", Toast.LENGTH_SHORT).show();

                        // debug
                        Toast.makeText(CameraActivity.this, "Image: " + photoName + " has been saved successfully.", Toast.LENGTH_SHORT).show();
                        passImageNameToOutfitCreateActivity(photoName);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(CameraActivity.this, "Error saving photo" + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    // sample 14 - allow other activities to access string extra without knowing the TAG
    public static String getImageNameFromCameraActivity(Intent intent) {
        return intent.getStringExtra(EXTRA_IMAGE_NAME);
    }

    private void passImageNameToOutfitCreateActivity(String photoName) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IMAGE_NAME, photoName);
        setResult(Activity.RESULT_OK, intent);
//        finish();
    }

    public static File getPhotoFilePath(Context context, String photoName) {
        String imageFilePathName = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + photoName;
        Log.d("CameraActivity", "getPhotoFilePath: " + imageFilePathName);
        return new File(imageFilePathName);
    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
        return intent;
    }

    // hold option + move mouse key to look around
    // wasd to move around
    private void startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        // verify CameraProvider initialization succeeded
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, getExecutor());
    }

    // bind all use cases to camera in this function
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        // clear camera provider
        cameraProvider.unbindAll();

        // select which camera to use
        CameraSelector cameraSelector =
                new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

        // use case 1: view finder mode
        PreviewView previewView = findViewById(R.id.viewFinder);
        Preview preview =
                new Preview.Builder()
                        .setTargetRotation(Surface.ROTATION_0)
                        .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // use case 2: image capture
        imageCapture =
                new ImageCapture.Builder()
                        .setTargetRotation(Surface.ROTATION_0)
                        .build();

        // use case 3: image analysis - image editing
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setTargetRotation(previewView.getDisplay().getRotation())
                .build();

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageCapture, preview, imageAnalysis);
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        image.close();
    }
}