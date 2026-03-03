package com.example.expensetracker.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.adapters.ExpenseAdapter;
import com.example.expensetracker.databinding.ActivityMainBinding;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.viewmodels.ExpenseViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ExpenseAdapter.OnExpenseClickListener {

    private ActivityMainBinding binding;
    private ExpenseViewModel expenseViewModel;
    private ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        // Setup RecyclerView
        binding.rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        expenseAdapter = new ExpenseAdapter(new ArrayList<>(), this);
        binding.rvExpenses.setAdapter(expenseAdapter);

        // Setup filter buttons
        setupFilterButtons();

        // FAB click listener
        binding.fabAddExpense.setOnClickListener(v ->
                startActivity(new Intent(this, CameraActivity.class)));

        // Observe ViewModel
        observeViewModel();
    }

    private void setupFilterButtons() {
        binding.btnDayFilter.setOnClickListener(v -> expenseViewModel.setFilterType("DAY"));
        binding.btnWeekFilter.setOnClickListener(v -> expenseViewModel.setFilterType("WEEK"));
        binding.btnMonthFilter.setOnClickListener(v -> expenseViewModel.setFilterType("MONTH"));
        binding.btnAllFilter.setOnClickListener(v -> expenseViewModel.setFilterType("ALL"));
        binding.btnDatePicker.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    Calendar selected = Calendar.getInstance();
                    selected.set(year, month, day);
                    expenseViewModel.setFilterDate(selected.getTime());
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void observeViewModel() {
        expenseViewModel.getFilteredExpenses().observe(this, expenses -> {
            if (expenses != null) {
                expenseAdapter.setExpenses(expenses);
            }
        });

        expenseViewModel.getTotalExpenses().observe(this, total -> {
            if (total != null) {
                binding.tvTotalExpenses.setText(getString(R.string.total_expenses, total));
            }
        });

        expenseViewModel.getCategoryTotals().observe(this, categoryMap -> {
            if (categoryMap != null) {
                updateCategoryChart(categoryMap);
            }
        });
    }

    private void updateCategoryChart(Map<String, Double> categoryMap) {
        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
            entries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
        }

        if (!entries.isEmpty()) {
            PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
            dataSet.setColors(getResources().getIntArray(R.array.chart_colors));
            dataSet.setValueTextSize(12f);
            PieData data = new PieData(dataSet);
            binding.pieChart.setData(data);
            binding.pieChart.getDescription().setEnabled(false);
            binding.pieChart.setEntryLabelColor(Color.BLACK);
            binding.pieChart.animateY(1000);
            binding.pieChart.invalidate();
        } else {
            binding.pieChart.clear();
            binding.pieChart.invalidate();
        }
    }

    @Override
    public void onExpenseClick(Expense expense) {
        Intent intent = new Intent(this, ExpenseDetailActivity.class);
        intent.putExtra("expense_id", expense.id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            // Implement advanced filter if needed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}