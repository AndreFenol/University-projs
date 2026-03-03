package com.yourname.receiptify.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yourname.receiptify.R;
import com.yourname.receiptify.adapters.ExpenseAdapter;
import com.yourname.receiptify.database.ExpenseDatabase;
import com.yourname.receiptify.database.entities.Expense;
import java.util.List;

public class ExpensesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private ExpenseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        database = ExpenseDatabase.getDatabase(getContext());

        recyclerView = view.findViewById(R.id.recycler_expenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadExpenses();

        return view;
    }

    private void loadExpenses() {
        List<Expense> expenses = database.expenseDao().getAllExpenses();
        adapter = new ExpenseAdapter(expenses, getContext());
        recyclerView.setAdapter(adapter);
    }
}