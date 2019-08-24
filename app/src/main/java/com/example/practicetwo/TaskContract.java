package com.example.practicetwo;

import androidx.recyclerview.widget.RecyclerView;

import com.example.practicetwo.entity.Task;
import java.util.List;

public interface TaskContract {

    interface TaskView {
        void showTasks(List<Task> tasks);
        void showActivityToEditTask(Task task);
    }

    interface TaskPresenter {
        RecyclerView.Adapter getAdapter();
        void refresh();
        void getTasks();
        void addTask(Task task);
        void editTask(Task task);
        void showEditActivity(Task task);
        void changeFavourite(Task task);
        void deleteTask(Task task);
    }

}

