package com.example.outfitvault;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Surface;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

import com.example.outfitvault.model.PhotoHelper;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity implements ImageAnalysis.Analyzer {
    public static final String EXTRA_IMAGE_NAME = "com.example.outfitvault.CameraActivity - imageName";
    private static final String TAG = "com.example.outfitvault.CameraActivity";
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        startCamera();
        wireTakePhotoButton();

        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

    private void wireTakePhotoButton() {
        Button takePhotoButton = findViewById(R.id.btn_camera_capture);
        takePhotoButton.setOnClickListener(view -> {
            takePhoto();
        });
    }

    private void takePhoto() {
        String photoName = "" + System.currentTimeMillis() + ".jpg";
        File photoFilePath = PhotoHelper.getPhotoFile(CameraActivity.this, photoName);

        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(photoFilePath).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Toast.makeText(
                                CameraActivity.this,
                                "Image has been saved successfully",
                                Toast.LENGTH_SHORT).show();

                        // debug
                        Log.d(TAG, "onImageSaved: " + "Image: " + photoName);

                        passImageNameToOutfitCreateActivity(photoName);
                        finish();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(
                                CameraActivity.this,
                                "Error saving photo" + exception.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public static String getImageName(Intent intent) {
        return intent.getStringExtra(EXTRA_IMAGE_NAME);
    }

    private void passImageNameToOutfitCreateActivity(String photoName) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IMAGE_NAME, photoName);
        setResult(Activity.RESULT_OK, intent);
    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
        return intent;
    }

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
        PreviewView previewView = findViewById(R.id.view_finder);
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
                .setTargetRotation(Surface.ROTATION_0)
                .build();

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageCapture, preview, imageAnalysis);
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        image.close();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}