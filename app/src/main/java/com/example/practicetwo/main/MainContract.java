package com.example.practicetwo.main;

import com.example.practicetwo.entity.Task;

import java.util.List;

public interface MainContract {

    interface View{
        void showTasks(List<Task> tasks);
        void refreshView();
    }

    interface Presenter{
        void getAllTasks();
        void getFavouriteTasks();
        void addTask(Task task);
        void editTask(Task task);
        void changeFavourite(Task task);
        void deleteTask(Task task);
    }

}

