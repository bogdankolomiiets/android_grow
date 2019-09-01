package com.example.practicetwo;

import com.example.practicetwo.entity.Task;

public interface TaskContract {

    interface TaskView {
        CustomRecyclerView getAdapter();
        void showActivityToEditTask(Task task);
    }

    interface TaskPresenter{
        void refreshData();
        void insertTask(Task task);
        void updateTask(Task task);
        void showEditActivity(Task task);
        void changeFavourite(String taskId);
        void deleteTask(String taskId);
    }
}

