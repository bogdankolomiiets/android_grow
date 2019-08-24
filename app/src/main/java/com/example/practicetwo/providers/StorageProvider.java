package com.example.practicetwo.providers;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.TaskContract;

import java.util.ArrayList;
import java.util.List;

public interface StorageProvider{
    List<TaskContract.TaskPresenter> callBackViewListeners = new ArrayList<>();

    default void addCallBackViewListener(TaskContract.TaskPresenter callBackViewListener){
        callBackViewListeners.add(callBackViewListener);
    }

    void addTask(Task task);
    void changeTaskFavouriteValue(Task task);
    void editTask(Task task);
    void deleteTask(Task task);
    List<Task> getAllTasks();
    List<Task> getFavouriteTasks();
}
