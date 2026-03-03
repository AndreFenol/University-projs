package com.example.expensetracker.models;

public class ReceiptItem {
    public String name;
    public double price;

    public ReceiptItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}