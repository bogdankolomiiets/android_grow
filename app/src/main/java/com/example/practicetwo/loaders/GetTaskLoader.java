package com.example.practicetwo.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;

import java.util.List;

public class GetTaskLoader extends AsyncTaskLoader<List<Task>> {
    private StorageProvider storageProvider;
    private final boolean isFavouriteTasks;

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    public GetTaskLoader(@NonNull Context context, StorageProvider storageProvider, boolean isFavouriteTasks) {
        super(context);
        this.storageProvider = storageProvider;
        this.isFavouriteTasks = isFavouriteTasks;
    }

    @Nullable
    @Override
    public List<Task> loadInBackground() {
        return isFavouriteTasks ? storageProvider.getFavouriteTasks() : storageProvider.getAllTasks();
    }
}
