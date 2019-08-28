package com.example.practicetwo.presenters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicetwo.CustomRecyclerView;
import com.example.practicetwo.R;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.TaskContract;
import com.example.practicetwo.loaders.DeleteTaskLoader;
import com.example.practicetwo.loaders.InsertTaskLoader;
import com.example.practicetwo.loaders.UpdateFavouriteTaskLoader;
import com.example.practicetwo.loaders.UpdateTaskLoader;
import com.example.practicetwo.providers.StorageProvider;
import com.example.practicetwo.util.Constants;
import com.example.practicetwo.util.StorageFactory;
import com.example.practicetwo.loaders.GetTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class TaskPresenterImpl implements TaskContract.TaskPresenter, LoaderManager.LoaderCallbacks<List<Task>> {
    private static final String TAG = TaskPresenterImpl.class.getName();
    private Context context;
    private StorageProvider storageProvider;
    private TaskContract.TaskView taskView;
    private boolean showFavouriteTasks;
    private LoaderManager loaderManager;
    private Bundle loaderBundle;

    public TaskPresenterImpl(){}

    public TaskPresenterImpl(View view, TaskContract.TaskView taskView, boolean showFavouriteTasks, LoaderManager loaderManager) {
        Log.d("TAG", "TaskPresenterImpl: showFavouriteTasks = " + showFavouriteTasks);
        this.taskView = taskView;
        this.context = view.getContext();
        this.storageProvider = StorageFactory.getInstance().getFactory(view);
//        storageProvider.addCallBackViewListener(this);
        this.showFavouriteTasks = showFavouriteTasks;
        this.loaderManager = loaderManager;
        loaderManager.initLoader(R.integer.GET_LOADER, Bundle.EMPTY, this);
        loaderBundle = new Bundle();
    }

    @Override
    public void refresh() {
        //todo
    }

    @Override
    public void notifyAdapter() {
        taskView.updateView();
    }

    @Override
    public void insertTask(Task task) {
        loaderBundle.clear();
        loaderBundle.putParcelable(Constants.TASK, task);
        loaderManager.restartLoader(R.integer.INSERT_LOADER, loaderBundle, this);
    }

    @Override
    public void updateTask(Task task) {
        loaderBundle.clear();
        loaderBundle.putParcelable(Constants.TASK, task);
        loaderManager.restartLoader(R.integer.UPDATE_LOADER, loaderBundle, this);
    }

    @Override
    public void showEditActivity(Task task) {
        taskView.showActivityToEditTask(task);
    }

    @Override
    public void changeFavourite(Task task) {
        task.setFavourite(!task.isFavourite());
        loaderBundle.clear();
        loaderBundle.putParcelable(Constants.TASK, task);
        loaderManager.restartLoader(R.integer.UPDATE_FAVOURITE_LOADER, loaderBundle, this);
    }

    @Override
    public void deleteTask(Task task) {
        loaderBundle.clear();
        loaderBundle.putParcelable(Constants.TASK, task);
        loaderManager.restartLoader(R.integer.DELETE_LOADER, loaderBundle, this);
    }
//
//    @Override
//    public void removeCallBackViewListener() {
//        storageProvider.removeCallBackViewListener(this);
//    }

    @NonNull
    @Override
    public Loader<List<Task>> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case R.integer.GET_LOADER:
                return new GetTaskLoader(context, storageProvider, showFavouriteTasks);
            case R.integer.INSERT_LOADER:
                return new InsertTaskLoader(context, storageProvider, showFavouriteTasks, args);
            case R.integer.DELETE_LOADER:
                return new DeleteTaskLoader(context, storageProvider, showFavouriteTasks, args);
            case R.integer.UPDATE_LOADER:
                return new UpdateTaskLoader(context, storageProvider, showFavouriteTasks, args);
            case R.integer.UPDATE_FAVOURITE_LOADER:
                return new UpdateFavouriteTaskLoader(context, storageProvider, showFavouriteTasks, args);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Task>> loader, List<Task> data) {
        taskView.getAdapter().setData(data);
        notifyAdapter();
    }


    @Override
    public void onLoaderReset(@NonNull Loader<List<Task>> loader) {
        Log.d(TAG, "onLoaderReset: ");
    }
}
