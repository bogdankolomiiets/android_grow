package com.example.practicetwo.main;

import com.example.practicetwo.entity.Task;
import java.util.List;

public interface MainContract {

    interface View{
        void showTasks();
    }

    interface Presenter{
        List<Task> getTaskFromStorage();
        List<Task> getFavouriteTaskFromStorage();
        boolean saveTask(Task task);
        void onDestroy();
    }

}

