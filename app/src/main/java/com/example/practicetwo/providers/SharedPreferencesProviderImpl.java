package com.example.practicetwo.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.example.practicetwo.util.Constants;
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
    private List<Task> tasksList;
    private Gson gson;

    public SharedPreferencesProviderImpl(Context context) {
        this.tasksList = new ArrayList<>();
        this.context = context;
        this.gson = new Gson();
        preferences = context.getSharedPreferences(Constants.SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void addTask(Task task) {
        readTasks();
        tasksList.add(task);
        if (writeTasks()){
            showToast(R.string.taskSaved);
        } else showToast(R.string.taskNotSaved);
    }

    @Override
    public void changeTaskFavouriteValue(Task task) {
        readTasks();
        for (Task t : tasksList) {
            if (t.getId().equals(task.getId())) {
                t.setFavourite(task.isFavourite());
                showToast(R.string.taskChanged);
                break;
            }
        }
        writeTasks();
    }

    @Override
    public void editTask(Task task) {
        readTasks();
        for (int i = 0; i < tasksList.size(); i++) {
            if (tasksList.get(i).getId().equals(task.getId())) {
                tasksList.set(i, task);
                showToast(R.string.taskChanged);
                break;
            }
        }
        writeTasks();
    }

    @Override
    public void deleteTask(Task task) {
        readTasks();
        if (tasksList.remove(task)){
            showToast(R.string.taskRemoved);
            writeTasks();
        } else showToast(R.string.taskNotRemoved);
    }

    @Override
    public List<Task> getAllTasks() {
        readTasks();
        return tasksList;
    }

    @Override
    public List<Task> getFavouriteTasks() {
        readTasks();
        List<Task> favouriteTasks = new ArrayList<>();
            for (Task task : tasksList) {
                if (task.isFavourite()) {
                    favouriteTasks.add(task);
                }
            }
        return favouriteTasks;
    }

    private boolean writeTasks(){
        try {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.TASKS, gson.toJson(tasksList));
            editor.apply();
            notifyViews();
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    private void readTasks(){
        tasksList.clear();
        String tasks = preferences.getString(Constants.TASKS, null);
        if (tasks != null) {
            JsonArray jsonArray = new JsonParser().parse(tasks).getAsJsonArray();
            for (JsonElement o : jsonArray) {
                tasksList.add(gson.fromJson(o, Task.class));
            }
        }
    }

    private void showToast(int msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}