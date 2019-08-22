package com.example.practicetwo.providers;

import android.content.Context;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.main.MainContract;

import java.util.List;

public class ExternalStorageProviderImpl implements StorageProvider {
    private Context context;
    private MainContract.View callBackView;

    public ExternalStorageProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void changeTaskFavouriteValue(Task task) {

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

    @Override
    public void addCallBackViewListener(MainContract.View callBackView) {
        this.callBackView = callBackView;
    }

    @Override
    public void removeCallBackViewListener(MainContract.View callBackViewListener) {

    }
}
