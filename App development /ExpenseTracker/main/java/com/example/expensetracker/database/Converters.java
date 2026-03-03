package com.example.expensetracker.database;

import androidx.room.TypeConverter;

import com.example.expensetracker.models.ReceiptItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static List<ReceiptItem> fromString(String value) {
        if (value == null) return null;
        Type listType = new TypeToken<List<ReceiptItem>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<ReceiptItem> list) {
        if (list == null) return null;
        return new Gson().toJson(list);
    }
}