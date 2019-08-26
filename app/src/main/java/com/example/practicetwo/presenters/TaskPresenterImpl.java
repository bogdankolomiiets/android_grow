package com.example.practicetwo.presenters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.practicetwo.CustomRecyclerView;
import com.example.practicetwo.R;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.TaskContract;
import com.example.practicetwo.providers.StorageProvider;
import com.example.practicetwo.util.StorageFactory;
import com.example.practicetwo.util.TaskLoader;

import java.util.List;

public class TaskPresenterImpl implements TaskContract.TaskPresenter, LoaderManager.LoaderCallbacks<List<Task>> {
    private static final String TAG = TaskPresenterImpl.class.getName();
    private Context context;
    private StorageProvider storageProvider;
    private TaskContract.TaskView taskView;
    private CustomRecyclerView adapter;
    private boolean showFavouriteTasks;
    private LoaderManager loaderManager;

    public TaskPresenterImpl(View view, TaskContract.TaskView taskView, boolean showFavouriteTasks, LoaderManager loaderManager) {
        this.taskView = taskView;
        this.context = view.getContext();
        this.storageProvider = StorageFactory.getInstance().getFactory(context);
        storageProvider.addCallBackViewListener(this);
        this.showFavouriteTasks = showFavouriteTasks;
        this.loaderManager = loaderManager;
        this.loaderManager.initLoader(R.integer.LOADER_ID, Bundle.EMPTY, this);
        adapter = taskView.getAdapter();
    }

//    private void getTasksList() {
//        loaderManager.initLoader(R.integer.LOADER_ID, Bundle.EMPTY, this).forceLoad();
//    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getTasks() {
        loaderManager.getLoader(R.integer.LOADER_ID).forceLoad();
        //taskView.setCustomAdapter(getTasksList());
}

    @Override
    public void addTask(Task task) {
        loaderManager.getLoader(R.integer.LOADER_ID).forceLoad();
        storageProvider.insertTask(task);
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
        task.setFavourite(!task.isFavourite());
        storageProvider.changeTaskFavouriteValue(task);
    }

    @Override
    public void deleteTask(Task task) {
        storageProvider.deleteTask(task);
    }

    @Override
    public void removeCallBackViewListener() {
        storageProvider.removeCallBackViewListener(this);
    }

    @NonNull
    @Override
    public Loader<List<Task>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader: ");
        return new TaskLoader(context, storageProvider, showFavouriteTasks);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Task>> loader, List<Task> data) {
        Log.d(TAG, "onLoadFinished: ");
        adapter.setData(data);
        storageProvider.notifyViews();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Task>> loader) {
        loader.cancelLoad();
        Log.d(TAG, "onLoaderReset: ");
    }
}
