package com.example.practicetwo.providers;

import android.content.Context;
import com.example.practicetwo.entity.Task;
import java.util.ArrayList;
import java.util.List;

public class DatabaseProviderImpl implements StorageProvider {
    private Context context;

    public DatabaseProviderImpl(Context context) {
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
