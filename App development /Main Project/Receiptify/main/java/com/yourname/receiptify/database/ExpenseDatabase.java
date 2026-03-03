package com.yourname.receiptify.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;
import com.yourname.receiptify.database.entities.Expense;
import com.yourname.receiptify.utils.DateConverter;

@Database(entities = {Expense.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class ExpenseDatabase extends RoomDatabase {
    public abstract ExpenseDao expenseDao();

    private static volatile ExpenseDatabase INSTANCE;

    public static ExpenseDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpenseDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ExpenseDatabase.class, "expense_database")
                            .allowMainThreadQueries() // Only for demo - use background threads in production
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}