package com.example.practicetwo.providers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.practicetwo.R;
import com.example.practicetwo.database.TaskDatabase;
import com.example.practicetwo.entity.Task;

import java.util.List;

public class DatabaseProviderImpl implements StorageProvider {
    private Context context;
    private TaskDatabase database;
    private View view;

    public DatabaseProviderImpl(View view) {
        this.context = view.getContext();
        this.database = TaskDatabase.getInstance(context);
        this.view = view;
    }

//    public static DatabaseProviderImpl getInstance(Context context) {
//        if (provider == null) {
//            provider = new DatabaseProviderImpl(context);
//        }
//        return provider;
//    }

    @Override
    public void insertTask(Task task) {
        try {
            database.taskDAO().insertTask(task);
            showToast(R.string.taskSaved);
            notifyViews();
        } catch (Exception ex) {
            showToast(R.string.taskNotSaved);
        }
    }

    @Override
    public void changeTaskFavouriteValue(Task task) {
        editTask(task);
    }

    @Override
    public void editTask(Task task) {
        try {
            database.taskDAO().updateTask(task);
            showToast(R.string.taskChanged);
            notifyViews();
        } catch (Exception ex) {
            showToast(R.string.taskNotChanged);
        }
    }

    @Override
    public void deleteTask(Task task) {
        try {
            database.taskDAO().deleteTask(task);
            showToast(R.string.taskRemoved);
            notifyViews();
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
        view.post(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());
    }
}
