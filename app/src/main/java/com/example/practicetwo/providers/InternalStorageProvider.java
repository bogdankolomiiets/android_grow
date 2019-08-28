package com.example.practicetwo.providers;

import android.content.Context;
import android.view.View;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.util.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InternalStorageProvider extends BaseStorageProviderImpl {
    private File file;

    public InternalStorageProvider(View view) {
        super(view);
        tasksList = new ArrayList<>();
        initFile();
    }

//    public static InternalStorageProvider getInstance(Context context){
//        if (provider == null)
//            provider = new InternalStorageProvider(context);
//        return provider;
//    }

    private void initFile() {
        file = new File(context.getFilesDir().getPath() + File.separator + Constants.TASK_FILENAME);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean writeTasks() {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
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
    }

    @Override
    protected void readTasks() {
        tasksList.clear();
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = context.openFileInput(file.getName());
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
}
