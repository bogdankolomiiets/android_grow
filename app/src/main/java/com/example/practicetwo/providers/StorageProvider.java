package com.example.practicetwo.providers;

import com.example.practicetwo.entity.Task;
import java.util.List;

public interface StorageProvider{
    void insertTask(Task task);
    void updateTask(Task task);
    void deleteTask(String taskId);
    void changeTaskFavouriteValue(String taskId);
    List<Task> getAllTasks();
    List<Task> getFavouriteTasks();
}
