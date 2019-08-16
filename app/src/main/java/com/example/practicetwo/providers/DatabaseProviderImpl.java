package com.example.practicetwo.providers;

import com.example.practicetwo.entity.Task;
import java.util.List;

public class DatabaseProviderImpl implements StorageProvider {
    @Override
    public boolean setToStorage(Task task) {
        return false;
    }

    @Override
    public List<Task> getFromStorage() {
        return null;
    }
}
