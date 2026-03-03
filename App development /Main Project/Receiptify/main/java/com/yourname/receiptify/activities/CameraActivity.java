package com.yourname.receiptify.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.yourname.receiptify.R;
import com.yourname.receiptify.ml.TextRecognitionHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity implements TextRecognitionHelper.OnTextRecognitionListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    private static final String TAG = "CameraActivity";

    private ImageView ivCapturedImage;
    private Button btnCapture, btnRetake, btnProcess;
    private ProgressBar progressBar;

    private String currentPhotoPath;
    private Bitmap capturedBitmap;
    private TextRecognitionHelper textRecognitionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initViews();
        setupClickListeners();

        textRecognitionHelper = new TextRecognitionHelper();

        // Check camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    private void initViews() {
        ivCapturedImage = findViewById(R.id.iv_captured_image);
        btnCapture = findViewById(R.id.btn_capture);
        btnRetake = findViewById(R.id.btn_retake);
        btnProcess = findViewById(R.id.btn_process);
        progressBar = findViewById(R.id.progress_bar);

        // Initially hide retake and process buttons
        btnRetake.setVisibility(View.GONE);
        btnProcess.setVisibility(View.GONE);
    }

    private void setupClickListeners() {
        btnCapture.setOnClickListener(v -> dispatchTakePictureIntent());
        btnRetake.setOnClickListener(v -> retakePhoto());
        btnProcess.setOnClickListener(v -> processImage());
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, "Error occurred while creating the File", ex);
                Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show();
                return;
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.yourname.receiptify.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "RECEIPT_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Load the full-size image
            setPic();

            // Show retake and process buttons, hide capture button
            btnCapture.setVisibility(View.GONE);
            btnRetake.setVisibility(View.VISIBLE);
            btnProcess.setVisibility(View.VISIBLE);
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = ivCapturedImage.getWidth();
        int targetH = ivCapturedImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        capturedBitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        ivCapturedImage.setImageBitmap(capturedBitmap);
    }

    private void retakePhoto() {
        // Reset UI
        btnCapture.setVisibility(View.VISIBLE);
        btnRetake.setVisibility(View.GONE);
        btnProcess.setVisibility(View.GONE);
        ivCapturedImage.setImageResource(R.drawable.ic_camera_placeholder);

        // Clear captured data
        capturedBitmap = null;
        currentPhotoPath = null;
    }

    private void processImage() {
        if (capturedBitmap == null) {
            Toast.makeText(this, "No image to process", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show loading
        progressBar.setVisibility(View.VISIBLE);
        btnProcess.setEnabled(false);

        // Process image with ML Kit
        textRecognitionHelper.recognizeText(capturedBitmap, this);
    }

    @Override
    public void onSuccess(String recognizedText) {
        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            btnProcess.setEnabled(true);

            Log.d(TAG, "Recognized text: " + recognizedText);

            // Navigate to ExpenseDetailActivity with recognized text
            Intent intent = new Intent(this, ExpenseDetailActivity.class);
            intent.putExtra("recognized_text", recognizedText);
            intent.putExtra("image_path", currentPhotoPath);
            startActivity(intent);

            finish(); // Close camera activity
        });
    }

    @Override
    public void onError(Exception e) {
        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            btnProcess.setEnabled(true);

            Log.e(TAG, "Text recognition failed", e);
            Toast.makeText(this, "Failed to process image: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
