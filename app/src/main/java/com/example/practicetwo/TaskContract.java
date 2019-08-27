package com.example.practicetwo;

import com.example.practicetwo.entity.Task;

public interface TaskContract {

    interface TaskView {
        CustomRecyclerView getAdapter();
        void updateView();
        void showActivityToEditTask(Task task);
    }

    interface TaskPresenter {
        void refresh();
        void notifyAdapter();
        void insertTask(Task task);
        void updateTask(Task task);
        void showEditActivity(Task task);
        void changeFavourite(Task task);
        void deleteTask(Task task);
//        void removeCallBackViewListener();
    }

}

