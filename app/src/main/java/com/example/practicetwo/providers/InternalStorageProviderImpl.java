package com.example.practicetwo.providers;

import android.content.Context;

import com.example.practicetwo.entity.Task;
import java.util.List;

public class InternalStorageProviderImpl implements StorageProvider {
    @Override
    public boolean setToStorage(Context context, Task task) {
        return false;
    }

    @Override
    public List<Task> getFromStorage() {
        return null;
    }
}
