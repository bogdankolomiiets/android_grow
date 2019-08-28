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

public class DeleteTaskLoader extends AsyncTaskLoader<List<Task>> {
    private StorageProvider storageProvider;
    private boolean showFavouriteTasks;
    private Task taskToDelete;

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    public DeleteTaskLoader(@NonNull Context context, StorageProvider storageProvider, boolean showFavouriteTasks, Bundle bundle) {
        super(context);
        this.storageProvider = storageProvider;
        this.showFavouriteTasks = showFavouriteTasks;
        taskToDelete = bundle.getParcelable(Constants.TASK);
    }

    @Nullable
    @Override
    public List<Task> loadInBackground() {
        storageProvider.deleteTask(taskToDelete);
        return showFavouriteTasks ? storageProvider.getFavouriteTasks() : storageProvider.getAllTasks();
    }

    @Override
    public void deliverResult(@Nullable List<Task> data) {
        super.deliverResult(data);

    }
}