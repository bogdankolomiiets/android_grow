package com.example.practicetwo.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;

import java.util.List;

public class GetTaskLoader extends AsyncTaskLoader<Object> {
    private StorageProvider storageProvider;
    private final boolean showFavouriteTasks;

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    public GetTaskLoader(@NonNull Context context, StorageProvider storageProvider, boolean showFavouriteTasks) {
        super(context);
        this.storageProvider = storageProvider;
        this.showFavouriteTasks = showFavouriteTasks;
    }

    @Nullable
    @Override
    public List<Task> loadInBackground() {
        return showFavouriteTasks ? storageProvider.getFavouriteTasks() : storageProvider.getAllTasks();
    }
}
