package com.example.expensetracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.expensetracker.database.AppDatabase;
import com.example.expensetracker.database.ExpenseDao;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseViewModel extends AndroidViewModel {

    private final ExpenseDao expenseDao;
    private final LiveData<List<Expense>> allExpenses;
    private final MutableLiveData<Date> filterDate = new MutableLiveData<>();
    private final MutableLiveData<String> filterType = new MutableLiveData<>("ALL");
    private final LiveData<List<Expense>> filteredExpenses;
    private final LiveData<Double> totalExpenses;
    private final LiveData<Map<String, Double>> categoryTotals;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        expenseDao = AppDatabase.getDatabase(application).expenseDao();
        allExpenses = expenseDao.getAllExpenses();

        // Initialize with current date
        Date now = new Date();
        filterDate.setValue(now);

        // Create filtered expenses LiveData
        filteredExpenses = Transformations.switchMap(filterType, type -> {
            if ("ALL".equals(type)) {
                return allExpenses;
            } else {
                return Transformations.switchMap(filterDate, date -> {
                    Date start = null, end = null;
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    switch (type) {
                        case "DAY":
                            start = DateUtils.getStartOfDay(cal.getTime());
                            end = DateUtils.getEndOfDay(cal.getTime());
                            break;
                        case "WEEK":
                            start = DateUtils.getStartOfWeek(cal.getTime());
                            end = DateUtils.getEndOfWeek(cal.getTime());
                            break;
                        case "MONTH":
                            start = DateUtils.getStartOfMonth(cal.getTime());
                            end = DateUtils.getEndOfMonth(cal.getTime());
                            break;
                    }

                    if (start != null && end != null) {
                        return expenseDao.getExpensesByDateRange(start, end);
                    }
                    return allExpenses;
                });
            }
        });

        // Create total expenses LiveData
        totalExpenses = Transformations.map(filteredExpenses, expenses -> {
            double total = 0;
            if (expenses != null) {
                for (Expense expense : expenses) {
                    total += expense.totalAmount;
                }
            }
            return total;
        });

        // Create category totals LiveData
        categoryTotals = Transformations.map(filteredExpenses, expenses -> {
            Map<String, Double> categoryMap = new HashMap<>();
            if (expenses != null) {
                for (Expense expense : expenses) {
                    Double current = categoryMap.get(expense.category);
                    categoryMap.put(expense.category,
                            (current == null ? 0 : current) + expense.totalAmount);
                }
            }
            return categoryMap;
        });
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    public LiveData<List<Expense>> getFilteredExpenses() {
        return filteredExpenses;
    }

    public LiveData<Double> getTotalExpenses() {
        return totalExpenses;
    }

    public LiveData<Map<String, Double>> getCategoryTotals() {
        return categoryTotals;
    }

    public void setFilterType(String type) {
        filterType.setValue(type);
    }

    public void setFilterDate(Date date) {
        filterDate.setValue(date);
    }

    public void insert(Expense expense) {
        AppDatabase.databaseWriteExecutor.execute(() -> expenseDao.insert(expense));
    }

    public void delete(Expense expense) {
        AppDatabase.databaseWriteExecutor.execute(() -> expenseDao.delete(expense));
    }
}