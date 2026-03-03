package com.yourname.receiptify.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    public static String formatCurrency(double amount) {
        return currencyFormat.format(amount);
    }

    public static double parseCurrency(String currencyString) {
        try {
            // Remove currency symbols and parse
            String cleanString = currencyString.replaceAll("[^\\d.]", "");
            return Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}