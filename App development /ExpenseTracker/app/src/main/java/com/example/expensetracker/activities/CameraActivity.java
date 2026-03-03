package com.example.expensetracker.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.R;
import com.example.expensetracker.databinding.ActivityCameraBinding;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.models.ReceiptItem;
import com.example.expensetracker.utils.ReceiptParser;
import com.example.expensetracker.viewmodels.ExpenseViewModel;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity {

    private ActivityCameraBinding binding;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ExecutorService cameraExecutor;
    private TextRecognizer textRecognizer;
    private ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ML Kit Text Recognizer
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        cameraExecutor = Executors.newSingleThreadExecutor();

        // Capture button
        binding.btnCapture.setOnClickListener(v -> captureImage());

        // Start camera
        startCamera();
    }

    private void startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraUseCases(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Error starting camera", Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindCameraUseCases(ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
    }

    private void captureImage() {
        if (imageCapture == null) return;

        imageCapture.takePicture(ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageCapturedCallback() {
                    @Override
                    public void onCaptureSuccess(@NonNull ImageProxy image) {
                        processImage(image);
                        super.onCaptureSuccess(image);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(CameraActivity.this, "Capture failed: " + exception.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void processImage(ImageProxy imageProxy) {
        ByteBuffer buffer = imageProxy.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        int rotationDegrees = imageProxy.getImageInfo().getRotationDegrees();
        Bitmap rawBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        // Rotate bitmap if needed
        Bitmap bitmap;
        if (rotationDegrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotationDegrees);
            bitmap = Bitmap.createBitmap(rawBitmap, 0, 0,
                    rawBitmap.getWidth(), rawBitmap.getHeight(), matrix, true);
            rawBitmap.recycle();
        } else {
            bitmap = rawBitmap;
        }

        InputImage image = InputImage.fromBitmap(bitmap, 0);

        textRecognizer.process(image)
                .addOnSuccessListener(visionText -> {
                    List<ReceiptItem> items = ReceiptParser.parseReceipt(visionText.getText());
                    if (items != null && !items.isEmpty()) {
                        showCategorySelection(items);
                    } else {
                        Toast.makeText(CameraActivity.this, "No items found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CameraActivity", "Text recognition error", e);
                    Toast.makeText(CameraActivity.this, "Processing failed", Toast.LENGTH_SHORT).show();
                })
                .addOnCompleteListener(task -> imageProxy.close());
    }

    private void showCategorySelection(List<ReceiptItem> items) {
        // For now, hardcode to Grocery - in real app, show a dialog
        saveExpense("Grocery", items);
    }

    private void saveExpense(String category, List<ReceiptItem> items) {
        double total = 0;
        for (ReceiptItem item : items) {
            total += item.price;
        }

        Expense expense = new Expense(category, total, new Date(), items);

        // Save to database using ViewModel
        ExpenseViewModel viewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        viewModel.insert(expense);

        Toast.makeText(this, "Expense saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textRecognizer != null) {
            textRecognizer.close();
        }
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
        }
    }
}