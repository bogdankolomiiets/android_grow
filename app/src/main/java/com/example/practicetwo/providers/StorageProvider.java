package com.example.practicetwo.providers;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.main.MainContract;

import java.util.List;

public interface StorageProvider{
    void addTask(Task task);
    void editTask(Task task);
    void deleteTask(Task task);
    List<Task> getAllTasks();
    List<Task> getFavouriteTasks();
}
