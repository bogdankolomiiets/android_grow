package com.example.practicetwo.loaders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;
import com.example.practicetwo.util.Constants;

public class UpdateTaskLoader extends AsyncTaskLoader<Object> {
    private StorageProvider storageProvider;
    private Task taskToUpdate;

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    public UpdateTaskLoader(@NonNull Context context, StorageProvider storageProvider, Bundle bundle) {
        super(context);
        this.storageProvider = storageProvider;
        taskToUpdate = bundle.getParcelable(Constants.TASK);
    }

    @Nullable
    @Override
    public Boolean loadInBackground() {
        return storageProvider.updateTask(taskToUpdate);
    }
}
