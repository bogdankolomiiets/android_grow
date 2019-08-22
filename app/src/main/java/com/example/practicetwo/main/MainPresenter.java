package com.example.practicetwo.main;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;

public class MainPresenter implements MainContract.Presenter {
    private StorageProvider storageProvider;
    private MainContract.View view;

    public MainPresenter(MainContract.View view, StorageProvider storageProvider) {
        this.view = view;
        this.storageProvider = storageProvider;
    }

    @Override
    public void getAllTasks() {
        view.showTasks(storageProvider.getAllTasks());
    }

    @Override
    public void getFavouriteTasks() {
        view.showTasks(storageProvider.getFavouriteTasks());
    }

    @Override
    public void addTask(Task task) {
        storageProvider.addTask(task);
    }

    @Override
    public void editTask(Task task) {
        storageProvider.editTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        storageProvider.deleteTask(task);
    }
}
