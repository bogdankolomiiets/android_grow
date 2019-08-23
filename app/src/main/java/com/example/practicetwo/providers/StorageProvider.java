package com.example.practicetwo.providers;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.main.MainContract;
import java.util.List;

public interface StorageProvider{
    void addTask(Task task);
    void changeTaskFavouriteValue(Task task);
    void editTask(Task task);
    void deleteTask(Task task);
    void addCallBackViewListener(MainContract.View callBackViewListener);
    void removeCallBackViewListener(MainContract.View callBackViewListener);
    List<Task> getAllTasks();
    List<Task> getFavouriteTasks();
}
