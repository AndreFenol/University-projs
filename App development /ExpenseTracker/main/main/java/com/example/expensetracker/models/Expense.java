package com.example.expensetracker.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.expensetracker.database.Converters;

import java.util.Date;
import java.util.List;

@Entity(tableName = "expenses")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String category;
    public double totalAmount;
    public Date date;

    @TypeConverters(Converters.class)
    public List<ReceiptItem> items;

    public Expense(String category, double totalAmount, Date date, List<ReceiptItem> items) {
        this.category = category;
        this.totalAmount = totalAmount;
        this.date = date;
        this.items = items;
    }
}