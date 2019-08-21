package com.example.practicetwo.main;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    private StorageProvider storageProvider;

    public MainPresenter(StorageProvider storageProvider) {
        this.storageProvider = storageProvider;
    }

    @Override
    public List<Task> getTaskFromStorage() {
        return storageProvider.getAllTaskFromStorage();
    }

    @Override
    public List<Task> getFavouriteTaskFromStorage() {
        return storageProvider.getFavouriteTaskFromStorage();
    }

    @Override
    public boolean saveTask(Task task) {
        return storageProvider.setToStorage(task);
    }

    @Override
    public void onDestroy() {

    }
}
