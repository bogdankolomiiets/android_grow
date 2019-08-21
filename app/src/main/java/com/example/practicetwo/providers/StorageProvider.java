package com.example.practicetwo.providers;

import com.example.practicetwo.entity.Task;
import java.util.List;

public interface StorageProvider{
    boolean setToStorage(Task task);
    List<Task> getAllTaskFromStorage();
    List<Task> getFavouriteTaskFromStorage();
}
