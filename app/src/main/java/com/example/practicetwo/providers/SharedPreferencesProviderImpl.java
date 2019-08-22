package com.example.practicetwo.providers;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.practicetwo.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesProviderImpl implements StorageProvider {
    private Context context;
    private SharedPreferences preferences;

    public SharedPreferencesProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void editTask(Task task) {

    }

    @Override
    public void deleteTask(Task task) {

    }

    @Override
    public List<Task> getAllTasks() {
        return null;
    }

    @Override
    public List<Task> getFavouriteTasks() {
        return null;
    }
}
