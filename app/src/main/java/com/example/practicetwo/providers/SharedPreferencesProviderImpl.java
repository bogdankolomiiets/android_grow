package com.example.practicetwo.providers;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.practicetwo.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesProviderImpl implements StorageProvider {
    private Context context;
    private SharedPreferences preferences;

    public SharedPreferencesProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean setToStorage(Task task) {
        return false;
    }

    @Override
    public List<Task> getAllTaskFromStorage() {
        return new ArrayList<>();
    }

    @Override
    public List<Task> getFavouriteTaskFromStorage() {
        return new ArrayList<>();
    }
}
