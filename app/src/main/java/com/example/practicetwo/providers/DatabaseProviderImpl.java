package com.example.practicetwo.providers;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.practicetwo.R;
import com.example.practicetwo.database.TaskDatabase;
import com.example.practicetwo.entity.Task;

import java.util.List;

public class DatabaseProviderImpl implements StorageProvider {
    private final Context context;
    private final TaskDatabase database;

    public DatabaseProviderImpl(Context context) {
        this.context = context;
        this.database = TaskDatabase.getInstance(context);
    }

    @Override
    public void insertTask(Task task) {
        try {
            database.taskDAO().insertTask(task);
            showToast(R.string.taskSaved);
        } catch (Exception ex) {
            showToast(R.string.taskNotSaved);
        }
    }

    @Override
    public void changeTaskFavouriteValue(String taskId) {
        Task task = database.taskDAO().getTask(taskId);
        task.setFavourite(!task.isFavourite());
        updateTask(task);
    }

    @Override
    public void updateTask(Task task) {
        try {
            database.taskDAO().updateTask(task);
            showToast(R.string.taskChanged);
        } catch (Exception ex) {
            showToast(R.string.taskNotChanged);
        }
    }

    @Override
    public void deleteTask(String taskId) {
        try {
            database.taskDAO().deleteTask(taskId);
            showToast(R.string.taskRemoved);
        } catch (Exception ex) {
            showToast(R.string.taskNotRemoved);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return database.taskDAO().getAllTasks();
    }

    @Override
    public List<Task> getFavouriteTasks() {
        return database.taskDAO().getFavouriteTasks();
    }

    private void showToast(int msg) {
        new Handler(context.getMainLooper()).post(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());
    }
}
