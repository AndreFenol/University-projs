package com.yourname.receiptify.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import com.yourname.receiptify.database.entities.Expense;

@Dao
public interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    List<Expense> getAllExpenses();

    @Query("SELECT * FROM expenses WHERE category = :category ORDER BY date DESC")
    List<Expense> getExpensesByCategory(String category);

    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    List<Expense> getExpensesByDateRange(long startDate, long endDate);

    @Query("SELECT category, SUM(amount) as total FROM expenses GROUP BY category")
    List<CategoryTotal> getCategoryTotals();

    @Insert
    void insertExpense(Expense expense);

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    @Query("DELETE FROM expenses")
    void deleteAllExpenses();
}