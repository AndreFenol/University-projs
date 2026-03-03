package com.yourname.receiptify.ml;

import android.graphics.Bitmap;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class TextRecognitionHelper {
    private TextRecognizer recognizer;
    private OnTextRecognitionListener listener;

    public interface OnTextRecognitionListener {
        void onSuccess(String recognizedText);
        void onError(Exception e);
    }

    public TextRecognitionHelper() {
        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    }

    public void recognizeText(Bitmap bitmap, OnTextRecognitionListener listener) {
        this.listener = listener;
        InputImage image = InputImage.fromBitmap(bitmap, 0);

        recognizer.process(image)
                .addOnSuccessListener(this::processTextRecognitionResult)
                .addOnFailureListener(listener::onError);
    }

    private void processTextRecognitionResult(Text texts) {
        StringBuilder result = new StringBuilder();
        for (Text.TextBlock block : texts.getTextBlocks()) {
            for (Text.Line line : block.getLines()) {
                result.append(line.getText()).append("\n");
            }
        }
        listener.onSuccess(result.toString());
    }
}