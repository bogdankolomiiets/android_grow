package com.example.practicetwo.providers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.practicetwo.R;
import com.example.practicetwo.entity.Task;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseStorageProviderImpl implements StorageProvider {
    public List<Task> tasksList;
    public Context context;
    private View view;


    public BaseStorageProviderImpl(View view) {
        this.tasksList = new ArrayList<>();
        this.context = view.getContext();
        this.view = view;
    }

    protected abstract boolean writeTasks();

    protected abstract void readTasks();

    @Override
    public void insertTask(Task task) {
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

    protected void showToast(int msg){
        view.post(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());
    }

    protected void showToast(String msg){
        view.post(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());
    }
}
