package com.example.expensetracker.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.adapters.ReceiptItemAdapter;
import com.example.expensetracker.database.AppDatabase;
import com.example.expensetracker.database.ExpenseDao;
import com.example.expensetracker.models.Expense;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExpenseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        int expenseId = getIntent().getIntExtra("expense_id", -1);
        if (expenseId == -1) {
            finish();
            return;
        }

        // Load expense from database
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ExpenseDao expenseDao = AppDatabase.getDatabase(this).expenseDao();
            Expense expense = expenseDao.getExpenseById(expenseId);
            if (expense != null) {
                runOnUiThread(() -> displayExpense(expense));
            } else {
                finish();
            }
        });
    }

    private void displayExpense(Expense expense) {
        TextView tvCategory = findViewById(R.id.tvCategory);
        TextView tvTotal = findViewById(R.id.tvTotal);
        TextView tvDate = findViewById(R.id.tvDate);
        RecyclerView rvItems = findViewById(R.id.rvItems);

        tvCategory.setText(expense.category);
        tvTotal.setText(getString(R.string.total_amount, expense.totalAmount));

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        tvDate.setText(sdf.format(expense.date));

        // Setup items list
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        ReceiptItemAdapter adapter = new ReceiptItemAdapter(expense.items);
        rvItems.setAdapter(adapter);
    }
}