package com.example.practicetwo.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.practicetwo.util.Constants;
import com.example.practicetwo.entity.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    private static TaskDatabase instance;
    public abstract TaskDAO taskDAO();

    public static TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TaskDatabase.class, Constants.DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
