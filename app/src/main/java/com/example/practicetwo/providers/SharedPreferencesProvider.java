package com.example.practicetwo.providers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.practicetwo.util.Constants;
import com.example.practicetwo.entity.Task;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class SharedPreferencesProvider extends BaseStorageProviderImpl {
    private SharedPreferences preferences;
    private Gson gson;

    public SharedPreferencesProvider(Context context) {
        super(context);
        this.gson = new Gson();
        preferences = context.getSharedPreferences(Constants.SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    protected boolean writeTasks() {
        try {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.TASKS, gson.toJson(tasksList));
            editor.apply();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    protected void readTasks() {
        tasksList = new ArrayList<>();
        String tasks = preferences.getString(Constants.TASKS, null);
        if (tasks != null) {
            JsonArray jsonArray = new JsonParser().parse(tasks).getAsJsonArray();
            for (JsonElement o : jsonArray) {
                tasksList.add(gson.fromJson(o, Task.class));
            }
        }
    }
}