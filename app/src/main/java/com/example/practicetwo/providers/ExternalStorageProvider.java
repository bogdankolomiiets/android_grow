package com.example.practicetwo.providers;

import android.content.Context;
import android.os.Environment;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.util.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ExternalStorageProvider extends BaseStorageProviderImpl {
    private File file;

    public ExternalStorageProvider(Context context) {
        super(context);
        tasksList = new ArrayList<>();
    }

//    public static ExternalStorageProvider getInstance(Context context){
//        if (provider == null)
//            provider = new ExternalStorageProvider(context);
//        return provider;
//    }

    private boolean checkEnvironment() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(context.getExternalFilesDir(null), Constants.TASK_FILENAME);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else return false;
    }

    @Override
    protected boolean writeTasks() {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        if (checkEnvironment()) {
            try {
                fileOutputStream = new FileOutputStream(file);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(tasksList);
                notifyViews();
                return true;
            } catch (IOException ex) {
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
        } else {
            showToast(Environment.getExternalStorageState());
            return false;
        }
    }

    @Override
    protected void readTasks() {
        if (checkEnvironment()) {
            tasksList.clear();
            FileInputStream fileInputStream = null;
            ObjectInputStream objectInputStream = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                try {
                    fileInputStream = new FileInputStream(file);
                    if (fileInputStream.available() > 0) {
                        objectInputStream = new ObjectInputStream(fileInputStream);
                        tasksList.addAll((ArrayList<Task>) objectInputStream.readObject());
                    }
                } catch (IOException | ClassNotFoundException ex) {
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
        } else showToast(Environment.getExternalStorageState());
    }
}
