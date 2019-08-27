package com.example.practicetwo;

import com.example.practicetwo.entity.Task;

public interface TaskContract {

    interface TaskView {
        void showActivityToEditTask(Task task);
        CustomRecyclerView getAdapter();
    }

    interface TaskPresenter {
        void refresh();
        void getTasks();
        void addTask(Task task);
        void editTask(Task task);
        void showEditActivity(Task task);
        void changeFavourite(Task task);
        void deleteTask(Task task);
        void removeCallBackViewListener();
    }

}

