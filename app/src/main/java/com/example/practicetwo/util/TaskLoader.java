package com.example.practicetwo.util;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;
import java.util.List;

public class TaskLoader extends AsyncTaskLoader<List<Task>> {
    private static final String TAG = TaskLoader.class.getName();
    private Context context;
    private StorageProvider storageProvider;
    private boolean showFavouriteTasks;

    public TaskLoader(@NonNull Context context, StorageProvider storageProvider, boolean showFavouriteTasks) {
        super(context);
        this.storageProvider = storageProvider;
        this.showFavouriteTasks = showFavouriteTasks;
        this.context = context;
    }

    @Nullable
    @Override
    public List<Task> loadInBackground() {
        return showFavouriteTasks ? storageProvider.getFavouriteTasks() : storageProvider.getAllTasks();
    }
}
