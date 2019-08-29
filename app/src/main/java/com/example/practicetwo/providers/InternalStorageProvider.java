package com.example.practicetwo.providers;

import android.content.Context;

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

public class InternalStorageProvider extends BaseStorageProviderImpl {
    private File file;

    public InternalStorageProvider(Context context) {
        super(context, new ArrayList<>());
        initFile();
    }

    private void initFile() {
        file = new File(context.getFilesDir().getPath() + File.separator + Constants.TASK_FILENAME);
        try {
            file.createNewFile();
        } catch (IOException e) {
            showToast(e.toString());
        }
    }

    @Override
    protected boolean writeTasks() {
        try (FileOutputStream fileOutputStream = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(tasksList);
            return true;
        } catch (IOException ex) {
            showToast(ex.toString());
            return false;
        }
    }

    @Override
    protected void readTasks() {
        tasksList.clear();
        try (FileInputStream fileInputStream = context.openFileInput(file.getName())) {
            if (fileInputStream.available() > 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                tasksList.addAll((List<Task>) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException ex) {
            showToast(ex.toString());
        }
    }
}
