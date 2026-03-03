package com.yourname.receiptify.utils;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiptParser {
    private static final String TAG = "ReceiptParser";

    // Regex patterns for different receipt formats
    private static final Pattern TOTAL_PATTERN = Pattern.compile(
            "(?i)(?:total|amount|sum)\\s*:?\\s*\\$?([0-9]+(?:\\.[0-9]{2})?)",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern PRICE_PATTERN = Pattern.compile(
            "\\$?([0-9]+(?:\\.[0-9]{2})?)"
    );

    private static final Pattern ITEM_PATTERN = Pattern.compile(
            "^([a-zA-Z\\s]+)\\s+\\$?([0-9]+(?:\\.[0-9]{2})?)$"
    );

    public static class ReceiptItem {
        public String name;
        public double price;

        public ReceiptItem(String name, double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + " - $" + String.format("%.2f", price);
        }
    }

    public static class ParsedReceipt {
        public List<ReceiptItem> items;
        public double total;
        public String storeName;
        public String date;

        public ParsedReceipt() {
            this.items = new ArrayList<>();
            this.total = 0.0;
        }
    }

    public static ParsedReceipt parseReceipt(String recognizedText) {
        ParsedReceipt receipt = new ParsedReceipt();

        if (recognizedText == null || recognizedText.trim().isEmpty()) {
            return receipt;
        }

        String[] lines = recognizedText.split("\n");

        // Try to extract store name (usually first few lines)
        receipt.storeName = extractStoreName(lines);

        // Extract total amount
        receipt.total = extractTotal(recognizedText);

        // Extract individual items
        receipt.items = extractItems(lines);

        // If no items found but total exists, create a generic item
        if (receipt.items.isEmpty() && receipt.total > 0) {
            receipt.items.add(new ReceiptItem("Receipt Items", receipt.total));
        }

        Log.d(TAG, "Parsed receipt: " + receipt.items.size() + " items, total: $" + receipt.total);

        return receipt;
    }

    private static String extractStoreName(String[] lines) {
        // Look for store name in first few lines
        for (int i = 0; i < Math.min(3, lines.length); i++) {
            String line = lines[i].trim();
            if (!line.isEmpty() &&
                    !line.matches(".*\\d+.*") && // No numbers
                    line.length() > 3 &&
                    line.length() < 50) { // Reasonable length
                return line;
            }
        }
        return "Unknown Store";
    }

    private static double extractTotal(String text) {
        Matcher matcher = TOTAL_PATTERN.matcher(text);
        double maxAmount = 0.0;

        while (matcher.find()) {
            try {
                double amount = Double.parseDouble(matcher.group(1));
                if (amount > maxAmount) {
                    maxAmount = amount;
                }
            } catch (NumberFormatException e) {
                Log.w(TAG, "Failed to parse amount: " + matcher.group(1));
            }
        }

        return maxAmount;
    }

    private static List<ReceiptItem> extractItems(String[] lines) {
        List<ReceiptItem> items = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Skip lines that look like headers, totals, or store info
            if (isSkippableLine(line)) continue;

            // Try to match item pattern
            Matcher itemMatcher = ITEM_PATTERN.matcher(line);
            if (itemMatcher.find()) {
                try {
                    String itemName = itemMatcher.group(1).trim();
                    double price = Double.parseDouble(itemMatcher.group(2));

                    if (isValidItem(itemName, price)) {
                        items.add(new ReceiptItem(itemName, price));
                    }
                } catch (NumberFormatException e) {
                    Log.w(TAG, "Failed to parse item: " + line);
                }
            }
        }

        return items;
    }

    private static boolean isSkippableLine(String line) {
        String lowerLine = line.toLowerCase();
        return lowerLine.contains("total") ||
                lowerLine.contains("subtotal") ||
                lowerLine.contains("tax") ||
                lowerLine.contains("change") ||
                lowerLine.contains("cash") ||
                lowerLine.contains("card") ||
                lowerLine.contains("receipt") ||
                lowerLine.contains("thank") ||
                line.matches(".*\\d{2}/\\d{2}/\\d{4}.*") || // Date pattern
                line.matches(".*\\d{2}:\\d{2}.*"); // Time pattern
    }

    private static boolean isValidItem(String name, double price) {
        return name != null &&
                !name.trim().isEmpty() &&
                name.length() > 1 &&
                price > 0 &&
                price < 10000; // Reasonable price limit
    }

    // Utility method to get category suggestions based on item names
    public static String suggestCategory(List<ReceiptItem> items, String storeName) {
        if (storeName != null) {
            String lowerStoreName = storeName.toLowerCase();
            if (lowerStoreName.contains("supermarket") || lowerStoreName.contains("grocery")) {
                return "Groceries";
            } else if (lowerStoreName.contains("restaurant") || lowerStoreName.contains("cafe")) {
                return "Food";
            } else if (lowerStoreName.contains("gas") || lowerStoreName.contains("fuel")) {
                return "Transport";
            } else if (lowerStoreName.contains("pharmacy")) {
                return "Health";
            }
        }

        // Analyze item names
        int foodCount = 0;
        for (ReceiptItem item : items) {
            String lowerName = item.name.toLowerCase();
            if (lowerName.contains("food") || lowerName.contains("drink") ||
                    lowerName.contains("coffee") || lowerName.contains("meal")) {
                foodCount++;
            }
        }

        if (foodCount > items.size() / 2) {
            return "Food";
        }

        return "General";
    }
}
