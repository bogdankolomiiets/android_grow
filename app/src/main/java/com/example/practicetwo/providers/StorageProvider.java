package com.example.practicetwo.providers;

import com.example.practicetwo.entity.Task;
import java.util.List;

public interface StorageProvider{
    boolean insertTask(Task task);
    boolean updateTask(Task task);
    boolean deleteTask(String taskId);
    boolean changeTaskFavouriteValue(String taskId);
    List<Task> getAllTasks();
    List<Task> getFavouriteTasks();
}
