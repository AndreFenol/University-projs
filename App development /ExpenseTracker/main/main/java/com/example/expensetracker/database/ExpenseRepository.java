package com.example.expensetracker.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.expensetracker.models.Expense;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpenseRepository {
    private ExpenseDao expenseDao;
    private ExecutorService executor;

    public ExpenseRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        expenseDao = database.expenseDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return expenseDao.getAllExpenses();
    }

    public void insert(Expense expense) {
        executor.execute(() -> expenseDao.insert(expense));
    }

    public void delete(Expense expense) {
        executor.execute(() -> expenseDao.delete(expense));
    }
}