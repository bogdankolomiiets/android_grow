package com.example.practicetwo.loaders;

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

public class InsertTaskLoader extends AsyncTaskLoader<List<Task>> {
    private StorageProvider storageProvider;
    private final boolean showFavouriteTasks;
    private Task taskToInsert;

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    public InsertTaskLoader(@NonNull Context context, StorageProvider storageProvider, boolean showFavouriteTasks, Bundle bundle) {
        super(context);
        this.storageProvider = storageProvider;
        this.showFavouriteTasks = showFavouriteTasks;
        taskToInsert = bundle.getParcelable(Constants.TASK);
    }

    @Nullable
    @Override
    public List<Task> loadInBackground() {
        if (storageProvider.insertTask(taskToInsert)) {
            return showFavouriteTasks ? storageProvider.getFavouriteTasks() : storageProvider.getAllTasks();
        } else return null;
    }
}
