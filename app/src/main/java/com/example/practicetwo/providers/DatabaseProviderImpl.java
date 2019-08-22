package com.example.practicetwo.providers;

import android.content.Context;
import androidx.annotation.NonNull;
import com.example.practicetwo.database.TaskDatabase;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.main.MainContract;

import java.util.List;

public class DatabaseProviderImpl implements StorageProvider {
    private TaskDatabase database;
    private MainContract.View callBackView;

    public DatabaseProviderImpl(@NonNull Context context) {
        this.database = TaskDatabase.getInstance(context);
    }

    @Override
    public void addTask(Task task) {
        database.taskDAO().insertTask(task);
    }

    @Override
    public void changeTaskFavouriteValue(Task task) {
        database.taskDAO().updateTask(task);
    }

    @Override
    public void editTask(Task task) {

    }

    @Override
    public void deleteTask(Task task) {
        database.taskDAO().deleteTask(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return database.taskDAO().getAllTasks();
    }

    @Override
    public List<Task> getFavouriteTasks() {
        return database.taskDAO().getFavouriteTasks();
    }

    @Override
    public void addCallBackViewListener(MainContract.View callBackView) {
        this.callBackView = callBackView;
    }

    @Override
    public void removeCallBackViewListener(MainContract.View callBackViewListener) {

    }
}
