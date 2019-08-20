package com.example.practicetwo.main;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    @Override
    public List<Task> getTaskFromStorage(StorageProvider storageProvider) {
        return storageProvider.getFromStorage();
    }

    @Override
    public boolean saveTask(Task task) {
        return false;
    }

    @Override
    public void onDestroy() {

    }
}
