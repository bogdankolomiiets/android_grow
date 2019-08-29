package com.example.practicetwo;

import android.os.Parcelable;

import com.example.practicetwo.entity.Task;

public interface TaskContract {

    interface TaskView {
        CustomRecyclerView getAdapter();
        void updateView();
        void showActivityToEditTask(Task task);
    }

    interface TaskPresenter{
        void notifyAdapter();
        void refresh();
        void insertTask(Task task);
        void updateTask(Task task);
        void showEditActivity(Task task);
        void changeFavourite(String taskId);
        void deleteTask(String taskId);
    }

}

