package com.yourname.receiptify.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.yourname.receiptify.R;
import com.yourname.receiptify.database.ExpenseDatabase;

public class SettingsFragment extends Fragment {
    private ExpenseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        database = ExpenseDatabase.getDatabase(getContext());

        Button clearDataButton = view.findViewById(R.id.btn_clear_data);
        clearDataButton.setOnClickListener(v -> clearAllData());

        return view;
    }

    private void clearAllData() {
        database.expenseDao().deleteAllExpenses();
        Toast.makeText(getContext(), "All data cleared", Toast.LENGTH_SHORT).show();
    }
}