package com.example.practicetwo.loaders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;
import com.example.practicetwo.util.Constants;

import java.util.List;

public class InsertTaskLoader extends AsyncTaskLoader<List<Task>> {
    private StorageProvider storageProvider;
    private Task taskToInsert;
    private boolean isFavouriteTasks;

    public InsertTaskLoader(@NonNull Context context, StorageProvider storageProvider, Bundle bundle, boolean isFavouriteTasks) {
        super(context);
        this.storageProvider = storageProvider;
        taskToInsert = bundle.getParcelable(Constants.TASK);
        this.isFavouriteTasks = isFavouriteTasks;
    }

    @Nullable
    @Override
    public List<Task> loadInBackground() {
        storageProvider.insertTask(taskToInsert);
        return isFavouriteTasks ? storageProvider.getFavouriteTasks() : storageProvider.getAllTasks();
    }
}
