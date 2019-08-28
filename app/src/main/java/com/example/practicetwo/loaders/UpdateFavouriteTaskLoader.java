package com.example.practicetwo.loaders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.practicetwo.providers.StorageProvider;
import com.example.practicetwo.util.Constants;

public class UpdateFavouriteTaskLoader extends AsyncTaskLoader<Object> {
    private StorageProvider storageProvider;
    private String taskIdToUpdateFav;

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    public UpdateFavouriteTaskLoader(@NonNull Context context, StorageProvider storageProvider, Bundle bundle) {
        super(context);
        this.storageProvider = storageProvider;
        taskIdToUpdateFav = bundle.getString(Constants.TASK);
    }

    @Nullable
    @Override
    public Boolean loadInBackground() {
        return storageProvider.changeTaskFavouriteValue(taskIdToUpdateFav);
    }
}
