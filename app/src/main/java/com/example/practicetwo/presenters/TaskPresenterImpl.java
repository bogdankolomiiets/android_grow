package com.example.practicetwo.presenters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

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

import java.util.List;

public class TaskPresenterImpl
        implements  TaskContract.TaskPresenter,
                    LoaderManager.LoaderCallbacks<Object> {

    private static final String TAG = TaskPresenterImpl.class.getName();
    private final Context context;
    private StorageProvider storageProvider;
    private final TaskContract.TaskView taskView;
    private final boolean showFavouriteTasks;
    private final LoaderManager loaderManager;
    private Bundle loaderBundle;

    public TaskPresenterImpl(View view,
                             TaskContract.TaskView taskView,
                             boolean showFavouriteTasks,
                             LoaderManager loaderManager) {
        this.taskView = taskView;
        this.context = view.getContext();
        this.storageProvider = StorageFactory.getInstance().getFactory(context);
        this.showFavouriteTasks = showFavouriteTasks;
        this.loaderManager = loaderManager;
        loaderManager.initLoader(R.integer.GET_LOADER, Bundle.EMPTY, this);
        loaderBundle = new Bundle();
    }

    @Override
    public void refresh() {
        loaderManager.getLoader(R.integer.GET_LOADER).forceLoad();
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
    public void changeFavourite(String taskId) {
        loaderBundle.clear();
        loaderBundle.putString(Constants.TASK, taskId);
        loaderManager.restartLoader(R.integer.UPDATE_FAVOURITE_LOADER, loaderBundle, this);
    }

    @Override
    public void deleteTask(String taskId) {
        loaderBundle.clear();
        loaderBundle.putString(Constants.TASK, taskId);
        loaderManager.restartLoader(R.integer.DELETE_LOADER, loaderBundle, this);
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case R.integer.INSERT_LOADER:
                return new InsertTaskLoader(context, storageProvider, args);
            case R.integer.DELETE_LOADER:
                return new DeleteTaskLoader(context, storageProvider, args);
            case R.integer.UPDATE_LOADER:
                return new UpdateTaskLoader(context, storageProvider, args);
            case R.integer.UPDATE_FAVOURITE_LOADER:
                return new UpdateFavouriteTaskLoader(context, storageProvider, args);
            default:
                return new GetTaskLoader(context, storageProvider, showFavouriteTasks);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        if (loader.getId() != R.integer.GET_LOADER){
            loaderManager.restartLoader(R.integer.GET_LOADER, Bundle.EMPTY, this);
            return;
        }
            taskView.getAdapter().setData((List<Task>) data);
            notifyAdapter();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }
}
