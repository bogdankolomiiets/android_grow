package com.example.practicetwo.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.example.practicetwo.Constants;
import com.example.practicetwo.R;
import com.example.practicetwo.entity.Task;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesProviderImpl implements StorageProvider {
    private Context context;
    private SharedPreferences preferences;
    private List<Task> taskList;
    private Gson gson;

    public SharedPreferencesProviderImpl(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(Constants.SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.taskList = new ArrayList<>();
        this.gson = new Gson();
    }

    @Override
    public void addTask(Task task) {
        readTasks();
        taskList.add(task);
        if (writeTasks()){
            showToast(R.string.taskSaved);
        } else showToast(R.string.taskNotSaved);
    }

    @Override
    public void editTask(Task task) {
        readTasks();
        for (Task t : taskList){
            if (t.getId().equals(task.getId())){
                t.setFavourite(task.isFavourite());
            }
            writeTasks();
        }
    }

    @Override
    public void deleteTask(Task task) {
        if (taskList.remove(task)){
            showToast(R.string.taskRemoved);
            writeTasks();
        } else showToast(R.string.taskNotRemoved);
    }

    @Override
    public List<Task> getAllTasks() {
        readTasks();
        return taskList;
    }

    @Override
    public List<Task> getFavouriteTasks() {
        readTasks();
        List<Task> favouriteTasks = new ArrayList<>();
            for (Task task : taskList) {
                if (task.isFavourite()) {
                    favouriteTasks.add(task);
                }
            }
        return favouriteTasks;
    }

    private boolean writeTasks(){
        try {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.TASKS, gson.toJson(taskList));
            editor.apply();
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    private void readTasks(){
        taskList = new ArrayList<>();
        String tasks = preferences.getString(Constants.TASKS, null);
        if (tasks != null) {
            JsonArray jsonArray = new JsonParser().parse(tasks).getAsJsonArray();
            for (JsonElement o : jsonArray) {
                taskList.add(gson.fromJson(o, Task.class));
            }
        }
    }

    private void showToast(int msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
