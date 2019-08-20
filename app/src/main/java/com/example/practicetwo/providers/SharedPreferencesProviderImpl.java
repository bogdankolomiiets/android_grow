package com.example.practicetwo.providers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.practicetwo.Constants;
import com.example.practicetwo.entity.Task;
import java.util.List;

public class SharedPreferencesProviderImpl implements StorageProvider {
    private SharedPreferences preferences;

    @Override
    public boolean setToStorage(Context context, Task task) {
        preferences = context.getSharedPreferences(Constants.SHARE_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.apply();
        return false;
    }

    @Override
    public List<Task> getFromStorage() {
        return null;
    }

}
