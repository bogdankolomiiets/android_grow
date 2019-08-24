package com.example.practicetwo.providers;

import android.content.Context;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.TaskContract;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

public class InternalStorageProviderImpl implements StorageProvider {
    private Context context;
    private GsonBuilder gsonBuilder;
    private TaskContract.TaskView callBackView;

    public InternalStorageProviderImpl(Context context) {
        this.context = context;
        gsonBuilder = new GsonBuilder().setPrettyPrinting();
    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void changeTaskFavouriteValue(Task task) {

    }

    @Override
    public void editTask(Task task) {

    }

    @Override
    public void deleteTask(Task task) {

    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        Task task = new Task();
        task.setTitle("First task title");
        task.setDescription("First task description");
        taskList.add(task);
        Task task2 = new Task();
        task2.setTitle("Second task title");
        task2.setDescription("Second task description");
        taskList.add(task2);
        return taskList;    }

    @Override
    public List<Task> getFavouriteTasks() {
        List<Task> taskList = new ArrayList<>();
        Task task = new Task();
        task.setTitle("Favourite task title");
        task.setDescription("Favourite task description");
        taskList.add(task);
        return taskList;
    }
}
