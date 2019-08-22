package com.example.practicetwo.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.example.practicetwo.Constants;
import com.example.practicetwo.R;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.main.MainContract;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesProviderImpl implements StorageProvider {
    private Context context;
    private static List<MainContract.View> callBackViewListeners = new ArrayList<>();
    private SharedPreferences preferences;
    private List<Task> taskList;
    private Gson gson;
    private JsonArray jsonArray;

    public SharedPreferencesProviderImpl(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(Constants.SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
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
    public void changeTaskFavouriteValue(Task task) {
        readTasks();
        for (Task t : taskList) {
            if (t.getId().equals(task.getId())) {
                t.setFavourite(task.isFavourite());
                break;
            }
        }
        writeTasks();
    }

    @Override
    public void editTask(Task task) {
        readTasks();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId().equals(task.getId())) {
                taskList.set(i, task);
                break;
            }
        }
        writeTasks();
    }

    @Override
    public void deleteTask(Task task) {
        readTasks();
        if (taskList.remove(task)){
            showToast(R.string.taskRemoved);
            writeTasks();
        } else showToast(R.string.taskNotRemoved);
    }

    @Override
    public void addCallBackViewListener(MainContract.View callBackViewListener) {
        callBackViewListeners.add(callBackViewListener);
    }

    @Override
    public void removeCallBackViewListener(MainContract.View callBackViewListener) {
        callBackViewListeners.remove(callBackViewListener);
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
            notifyViews();
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    private void notifyViews() {
        for (MainContract.View view : callBackViewListeners) {
            view.refresh();
        }
    }

    private void readTasks(){
        taskList = new ArrayList<>();
        String tasks = preferences.getString(Constants.TASKS, null);
        if (tasks != null) {
            jsonArray = new JsonParser().parse(tasks).getAsJsonArray();
            for (JsonElement o : jsonArray) {
                taskList.add(gson.fromJson(o, Task.class));
            }
        }
    }

    private void showToast(int msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}