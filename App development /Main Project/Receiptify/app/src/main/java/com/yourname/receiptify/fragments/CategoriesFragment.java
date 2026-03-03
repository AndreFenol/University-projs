package com.yourname.receiptify.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yourname.receiptify.R;
import com.yourname.receiptify.adapters.CategoryAdapter;
import com.yourname.receiptify.database.CategoryTotal;
import com.yourname.receiptify.database.ExpenseDatabase;
import java.util.List;

public class CategoriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private ExpenseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        database = ExpenseDatabase.getDatabase(getContext());

        recyclerView = view.findViewById(R.id.recycler_categories);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        loadCategories();

        return view;
    }

    private void loadCategories() {
        List<CategoryTotal> categories = database.expenseDao().getCategoryTotals();
        adapter = new CategoryAdapter(categories, getContext());
        recyclerView.setAdapter(adapter);
    }
}