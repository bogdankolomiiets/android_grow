package com.example.practicetwo.providers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Adapter;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.util.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExternalStorageProvider extends BaseStorageProviderImpl {
    private File file;

    public ExternalStorageProvider(Context context) {
        super(context);
        tasksList = new ArrayList<>();
    }

    private boolean checkEnvironment() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(context.getExternalFilesDir(null), Constants.TASK_FILENAME);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    showToast(e.toString());
                }
            }
            return true;
        } else return false;
    }

    @Override
    protected boolean writeTasks() {
        if (checkEnvironment()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                    objectOutputStream.writeObject((Serializable) tasksList);
                    return true;
                } catch (IOException ex) {
                showToast(ex.toString());
                return false;
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
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                try (FileInputStream fileInputStream = new FileInputStream(file);
                     ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
                if (fileInputStream.available() > 0) {
                    Object tempObject = objectInputStream.readObject();
                    if (tempObject instanceof ArrayList) {
                        tasksList = ((ArrayList<Task>) tempObject);
                    }
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    showToast(ex.toString());
                }
            }
        } else showToast(Environment.getExternalStorageState());
    }
}
