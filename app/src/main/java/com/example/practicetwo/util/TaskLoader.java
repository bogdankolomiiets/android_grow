package com.example.practicetwo.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class TaskLoader extends AsyncTaskLoader<String> {

    public TaskLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return null;
    }

    @Override
    public void deliverResult(@Nullable String data) {
        super.deliverResult(data);
    }
}
