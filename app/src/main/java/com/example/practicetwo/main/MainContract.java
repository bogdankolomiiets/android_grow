package com.example.practicetwo.main;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;

import java.util.List;

public interface MainContract {

    interface View{
        void showTasks();
    }

    interface Presenter{
        List<Task> getTaskFromStorage(StorageProvider storageProvider);
        boolean saveTask(Task task);
        void onDestroy();
    }

}

