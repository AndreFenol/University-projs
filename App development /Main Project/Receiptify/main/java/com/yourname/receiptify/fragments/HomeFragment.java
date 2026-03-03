package com.yourname.receiptify.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yourname.receiptify.R;
import com.yourname.receiptify.adapters.ExpenseAdapter;
import com.yourname.receiptify.database.entities.Expense;
import com.yourname.receiptify.utils.CurrencyUtils;
import com.yourname.receiptify.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView tvMonthlySpending, tvWeeklySpending, tvMonthYear;
    private BarChart barChart;
    private PieChart pieChart;
    private RecyclerView rvRecentExpenses;
    private ExpenseAdapter expenseAdapter;
    private List<Expense> recentExpenses;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);
        setupRecyclerView();
        loadData();
        setupCharts();

        return view;
    }

    private void initViews(View view) {
        tvMonthlySpending = view.findViewById(R.id.tv_monthly_spending);
        tvWeeklySpending = view.findViewById(R.id.tv_weekly_spending);
        tvMonthYear = view.findViewById(R.id.tv_month_year);
        barChart = view.findViewById(R.id.bar_chart);
        pieChart = view.findViewById(R.id.pie_chart);
        rvRecentExpenses = view.findViewById(R.id.rv_recent_expenses);

        // Set current month and year
        Calendar calendar = Calendar.getInstance();
        String monthYear = DateUtils.getMonthYear(calendar.getTime());
        tvMonthYear.setText(monthYear);
    }

    private void setupRecyclerView() {
        recentExpenses = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(recentExpenses, getContext());
        rvRecentExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRecentExpenses.setAdapter(expenseAdapter);
    }

    private void loadData() {
        // Load sample data - replace with actual database queries
        loadSampleData();
        updateSpendingAmounts();
    }

    private void loadSampleData() {
        // Sample recent expenses
        recentExpenses.clear();

        Expense expense1 = new Expense();
        expense1.setDescription("Starbucks");
        expense1.setAmount(180.0);
        expense1.setCategory("Food");
        expense1.setDate(DateUtils.getCurrentDate());

        Expense expense2 = new Expense();
        expense2.setDescription("Grab");
        expense2.setAmount(320.0);
        expense2.setCategory("Transport");
        expense2.setDate(DateUtils.getCurrentDate());

        Expense expense3 = new Expense();
        expense3.setDescription("SM Supermarket");
        expense3.setAmount(2300.0);
        expense3.setCategory("Groceries");
        expense3.setDate(DateUtils.getCurrentDate());

        recentExpenses.add(expense1);
        recentExpenses.add(expense2);
        recentExpenses.add(expense3);

        expenseAdapter.notifyDataSetChanged();
    }

    private void updateSpendingAmounts() {
        double monthlyTotal = calculateMonthlyTotal();
        double weeklyTotal = calculateWeeklyTotal();

        tvMonthlySpending.setText(CurrencyUtils.formatCurrency(monthlyTotal));
        tvWeeklySpending.setText(CurrencyUtils.formatCurrency(weeklyTotal));
    }

    private double calculateMonthlyTotal() {
        // Calculate from database - using sample data for now
        return 12430.0;
    }

    private double calculateWeeklyTotal() {
        // Calculate from database - using sample data for now
        return 2340.0;
    }

    private void setupCharts() {
        setupBarChart();
        setupPieChart();
    }

    private void setupBarChart() {
        List<BarEntry> entries = new ArrayList<>();

        // Sample weekly data
        entries.add(new BarEntry(0f, 800f)); // Monday
        entries.add(new BarEntry(1f, 1200f)); // Tuesday
        entries.add(new BarEntry(2f, 500f)); // Wednesday
        entries.add(new BarEntry(3f, 1500f)); // Thursday
        entries.add(new BarEntry(4f, 900f)); // Friday
        entries.add(new BarEntry(5f, 2000f)); // Saturday
        entries.add(new BarEntry(6f, 700f)); // Sunday

        BarDataSet dataSet = new BarDataSet(entries, "Daily Spending");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
    }

    private void setupPieChart() {
        List<PieEntry> entries = new ArrayList<>();

        // Sample category data
        entries.add(new PieEntry(40f, "Food"));
        entries.add(new PieEntry(30f, "Transport"));
        entries.add(new PieEntry(20f, "Bills"));
        entries.add(new PieEntry(10f, "Others"));

        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();
    }
}