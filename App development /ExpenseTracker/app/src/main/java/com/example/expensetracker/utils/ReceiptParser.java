package com.example.expensetracker.utils;

import com.example.expensetracker.models.ReceiptItem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiptParser {

    public static List<ReceiptItem> parseReceipt(String receiptText) {
        List<ReceiptItem> items = new ArrayList<>();
        if (receiptText == null || receiptText.isEmpty()) {
            return items;
        }

        String[] lines = receiptText.split("\n");
        Pattern pattern = Pattern.compile("(.+?)\\s+([-+]?[0-9]*\\.?[0-9]+)\\s*$");

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                try {
                    String name = matcher.group(1).trim();
                    double price = Double.parseDouble(matcher.group(2));
                    items.add(new ReceiptItem(name, price));
                } catch (NumberFormatException e) {
                    // Skip invalid price formats
                }
            }
        }
        return items;
    }
}