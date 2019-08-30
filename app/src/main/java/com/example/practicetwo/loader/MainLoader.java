package com.example.practicetwo.loader;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;
import com.example.practicetwo.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.example.practicetwo.R.integer.DELETE;
import static com.example.practicetwo.R.integer.INSERT;
import static com.example.practicetwo.R.integer.UPDATE;
import static com.example.practicetwo.R.integer.UPDATE_FAVOURITE;
import static com.example.practicetwo.util.Constants.TASK;

public class MainLoader extends AsyncTaskLoader<List<Task>> {
    private StorageProvider storageProvider;
    private Task task;
    private int mode;
    private String taskId;
    private boolean isFavouriteTasks;

    public MainLoader(@NonNull Context context, StorageProvider storageProvider, Bundle bundle, boolean isFavouriteTasks) {
        super(context);
        this.storageProvider = storageProvider;
        this.isFavouriteTasks = isFavouriteTasks;
        if (!bundle.isEmpty()){
            mode = bundle.getInt(Constants.MODE, 0);
            if (mode == INSERT || mode == UPDATE) {
                task = bundle.getParcelable(TASK);
                if (task == null) mode = 0;
            } else if (mode == DELETE || mode == UPDATE_FAVOURITE){
                taskId = bundle.getString(TASK);
            }
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public List<Task> loadInBackground() {
        switch (mode){
            case INSERT:
                storageProvider.insertTask(task);
                break;
            case UPDATE:
                storageProvider.updateTask(task);
                break;
            case UPDATE_FAVOURITE:
                storageProvider.changeTaskFavouriteValue(taskId);
                break;
            case DELETE:
                storageProvider.deleteTask(taskId);
                break;
        }
        return isFavouriteTasks ? storageProvider.getFavouriteTasks() : storageProvider.getAllTasks();
    }
}
