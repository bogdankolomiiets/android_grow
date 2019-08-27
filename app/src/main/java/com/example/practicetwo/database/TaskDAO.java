package com.example.practicetwo.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.practicetwo.util.Constants;
import com.example.practicetwo.entity.Task;
import java.util.List;

@Dao
public interface TaskDAO extends Constants {

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM " + DB_NAME)
    List<Task> getAllTasks();

    @Query("SELECT * FROM " + DB_NAME + " WHERE " + COLUMN_FAVOURITE + " = 1")
    List<Task> getFavouriteTasks();
}
