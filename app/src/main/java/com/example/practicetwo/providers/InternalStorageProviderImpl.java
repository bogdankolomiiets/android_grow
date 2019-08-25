package com.example.practicetwo.providers;

import android.content.Context;
import android.widget.Toast;
import com.example.practicetwo.R;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.util.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class InternalStorageProviderImpl implements StorageProvider {
    private Context context;
    private List<Task> tasksList;
    private File file;


    public InternalStorageProviderImpl(Context context) {
        this.tasksList = new ArrayList<>();
        this.context = context;
        initFile();
    }

    private void initFile() {
        file = new File(context.getFilesDir().getPath() + File.separator + Constants.TASK_FILENAME);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tasksList);
            notifyViews();
            return true;
        } catch (IOException ex){
            showToast(ex.toString());
            return false;
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readTasks(){
        tasksList.clear();
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = context.openFileInput(file.getName());
            objectInputStream = new ObjectInputStream(fileInputStream);
            tasksList.addAll((ArrayList<Task>) objectInputStream.readObject());
        } catch (Exception ex){
            showToast(ex.toString());
        } finally {
            try {
                if (objectInputStream != null) objectInputStream.close();
                if (fileInputStream != null) fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showToast(int msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private void showToast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
