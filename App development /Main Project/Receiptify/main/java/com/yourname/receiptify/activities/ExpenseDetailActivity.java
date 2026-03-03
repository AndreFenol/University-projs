package com.yourname.receiptify.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.yourname.receiptify.R;
import com.yourname.receiptify.database.ExpenseDatabase;
import com.yourname.receiptify.database.entities.Expense;
import java.util.Date;

public class ExpenseDetailActivity extends AppCompatActivity {
    private EditText etDescription, etAmount, etCategory;
    private Button btnSave;
    private ExpenseDatabase database;
    private String recognizedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        database = ExpenseDatabase.getDatabase(this);

        etDescription = findViewById(R.id.et_description);
        etAmount = findViewById(R.id.et_amount);
        etCategory = findViewById(R.id.et_category);
        btnSave = findViewById(R.id.btn_save);

        // Get recognized text from intent
        recognizedText = getIntent().getStringExtra("recognized_text");
        if (recognizedText != null) {
            parseReceiptText(recognizedText);
        }

        btnSave.setOnClickListener(v -> saveExpense());
    }

    private void parseReceiptText(String text) {
        // Simple parsing logic - you can enhance this
        String[] lines = text.split("\n");
        for (String line : lines) {
            // Look for total amount (lines containing $ and numbers)
            if (line.contains("$") && line.matches(".*\\d+\\.\\d{2}.*")) {
                String amount = line.replaceAll("[^\\d.]", "");
                if (!amount.isEmpty()) {
                    etAmount.setText(amount);
                    break;
                }
            }
        }

        // Set default category
        etCategory.setText("General");
        etDescription.setText("Receipt Expense");
    }

    private void saveExpense() {
        String description = etDescription.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();
        String category = etCategory.getText().toString().trim();

        if (description.isEmpty() || amountStr.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);

            Expense expense = new Expense();
            expense.description = description;
            expense.amount = amount;
            expense.category = category;
            expense.date = new Date();
            expense.receiptImagePath = ""; // You can add image path here
            expense.items = ""; // You can add parsed items here

            database.expenseDao().insertExpense(expense);

            Toast.makeText(this, "Expense saved successfully!", Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
        }
    }
}