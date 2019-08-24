package com.example.practicetwo.presenters;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.example.practicetwo.CustomRecyclerView;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.TaskContract;
import com.example.practicetwo.providers.StorageProvider;
import com.example.practicetwo.util.StorageFactory;

import java.util.List;

public class TaskPresenterImpl implements TaskContract.TaskPresenter {
    private Context context;
    private StorageProvider storageProvider;
    private TaskContract.TaskView taskView;
    private CustomRecyclerView adapter;
    private boolean showFavouriteTasks;

    public TaskPresenterImpl(View view, TaskContract.TaskView taskView, boolean showFavouriteTasks) {
        this.taskView = taskView;
        this.context = view.getContext();
        this.storageProvider = StorageFactory.getInstance().getFactory(context);
        storageProvider.addCallBackViewListener(this);
        this.showFavouriteTasks = showFavouriteTasks;
        adapter = new CustomRecyclerView(context, this, getTasksList());
    }

    private List<Task> getTasksList() {
        return showFavouriteTasks ? storageProvider.getFavouriteTasks() : storageProvider.getAllTasks();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Override
    public void refresh() {
        adapter.setData(getTasksList());
    }

    @Override
    public void getTasks() {
        taskView.showTasks(getTasksList());
    }

    @Override
    public void addTask(Task task) {
        storageProvider.addTask(task);
    }

    @Override
    public void editTask(Task task) {
        storageProvider.editTask(task);
    }

    @Override
    public void showEditActivity(Task task) {
        taskView.showActivityToEditTask(task);
    }

    @Override
    public void changeFavourite(Task task) {
        task.setFavourite(task.isFavourite() ? false : true);
        storageProvider.changeTaskFavouriteValue(task);
    }

    @Override
    public void deleteTask(Task task) {
        storageProvider.deleteTask(task);
    }
}
