package com.example.practicetwo.loaders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.StorageProvider;
import com.example.practicetwo.util.Constants;

public class InsertTaskLoader extends AsyncTaskLoader<Object> {
    private StorageProvider storageProvider;
    private Task taskToInsert;

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    public InsertTaskLoader(@NonNull Context context, StorageProvider storageProvider, Bundle bundle) {
        super(context);
        this.storageProvider = storageProvider;
        taskToInsert = bundle.getParcelable(Constants.TASK);
    }

    @Nullable
    @Override
    public Boolean loadInBackground() {
        return storageProvider.insertTask(taskToInsert);
    }
}
