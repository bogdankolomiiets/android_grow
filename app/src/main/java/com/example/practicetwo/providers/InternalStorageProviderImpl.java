package com.example.practicetwo.providers;

import android.content.Context;
import android.util.Log;

import com.example.practicetwo.Constants;
import com.example.practicetwo.entity.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InternalStorageProviderImpl implements StorageProvider {
    private Context context;
    private GsonBuilder gsonBuilder;

    public InternalStorageProviderImpl(Context context) {
        this.context = context;
        gsonBuilder = new GsonBuilder().setPrettyPrinting();
    }

    @Override
    public boolean setToStorage(Task task) {
        FileOutputStream outputStream;
        Gson gson = gsonBuilder.create();
        String res = gson.toJson(task);

        try {
            outputStream = context.openFileOutput(Constants.TASK_FILENAME, Context.MODE_PRIVATE);
            outputStream.write(res.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<Task> getAllTaskFromStorage() {
        List<Task> taskList = new ArrayList<>();
        Task task = new Task();
        task.setTitle("First task title");
        task.setDescription("First task description");
        taskList.add(task);
        Task task2 = new Task();
        task2.setTitle("Second task title");
        task2.setDescription("Second task description");
        taskList.add(task2);
        return taskList;
    }

    @Override
    public List<Task> getFavouriteTaskFromStorage() {
        List<Task> taskList = new ArrayList<>();
        Task task = new Task();
        task.setTitle("Favourite task title");
        task.setDescription("Favourite task description");
        taskList.add(task);
        return taskList;
    }
}
