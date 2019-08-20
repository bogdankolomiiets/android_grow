package com.example.practicetwo.main;

import android.util.Log;
import android.widget.Toast;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    @Override
    public List<Task> getTaskFromStorage(StorageProvider storageProvider) {
        List<Task> taskList = new ArrayList<>();
        Task task = new Task();
        task.setTitle("First");
        task.setDescription("fkndgjlnmfkdmckdl");
        taskList.add(task);
        task = new Task();
        task.setTitle("second");
        task.setDescription(".pckvifknvmbkvc");
        taskList.add(task);
        return taskList;
//        return storageProvider.getFromStorage();
    }

    @Override
    public List<Task> getFavouriteTaskFromStorage(StorageProvider storageProvider) {
        return new ArrayList<>();
    }

    @Override
    public boolean saveTask(Task task) {
        return false;
    }

    @Override
    public void onDestroy() {

    }
}
