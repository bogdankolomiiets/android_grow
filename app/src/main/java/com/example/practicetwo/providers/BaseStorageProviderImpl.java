package com.example.practicetwo.providers;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.practicetwo.R;
import com.example.practicetwo.entity.Task;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseStorageProviderImpl implements StorageProvider {
    public List<Task> tasksList;
    public final Context context;

    public BaseStorageProviderImpl(Context context) {
        this.tasksList = new ArrayList<>();
        this.context = context;
    }

    protected abstract boolean writeTasks();

    protected abstract void readTasks();

    @Override
    public boolean insertTask(Task task) {
        readTasks();
        tasksList.add(task);
        if (writeTasks()){
            showToast(R.string.taskSaved);
            return true;
        } else {
            showToast(R.string.taskNotSaved);
            return false;
        }
    }

    @Override
    public boolean changeTaskFavouriteValue(String taskIs) {
        readTasks();
        for (Task tempTask : tasksList) {
            if (tempTask.getId().equals(taskIs)) {
                tempTask.setFavourite(!tempTask.isFavourite());
                showToast(R.string.taskChanged);
                writeTasks();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateTask(Task task) {
        readTasks();
        for (int i = 0; i < tasksList.size(); i++) {
            if (tasksList.get(i).getId().equals(task.getId())) {
                tasksList.set(i, task);
                showToast(R.string.taskChanged);
                writeTasks();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteTask(String taskId) {
        readTasks();
        Iterator iterator = tasksList.listIterator();
        while (iterator.hasNext()){
            if (((Task)iterator.next()).getId().equals(taskId)){
                iterator.remove();
                showToast(R.string.taskRemoved);
                writeTasks();
                return true;
            }
        }
       return false;
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
        new Handler(context.getMainLooper()).post(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());

    }

    protected void showToast(String msg){
        new Handler(context.getMainLooper()).post(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());
    }
}
