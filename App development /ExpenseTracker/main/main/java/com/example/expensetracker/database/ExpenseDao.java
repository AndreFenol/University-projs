package com.example.expensetracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.expensetracker.models.Expense;

import java.util.Date;
import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insert(Expense expense);

    @Delete
    void delete(Expense expense);

    @Query("SELECT * FROM expenses")
    LiveData<List<Expense>> getAllExpenses();

    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end")
    LiveData<List<Expense>> getExpensesByDateRange(Date start, Date end);

    @Query("SELECT SUM(totalAmount) FROM expenses")
    double getTotalExpenses();

    @Query("SELECT SUM(totalAmount) FROM expenses WHERE category = :category")
    double getCategoryTotal(String category);

    @Query("SELECT DISTINCT category FROM expenses")
    List<String> getAllCategories();

    @Query("SELECT * FROM expenses WHERE id = :id")
    Expense getExpenseById(int id);
}