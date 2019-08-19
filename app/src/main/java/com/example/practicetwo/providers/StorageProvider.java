package com.example.practicetwo.providers;

import android.content.Context;
import android.os.Parcelable;

import com.example.practicetwo.entity.Task;
import java.util.List;

public interface StorageProvider{
    boolean setToStorage(Context context, Task task);
    List<Task> getFromStorage();
}
