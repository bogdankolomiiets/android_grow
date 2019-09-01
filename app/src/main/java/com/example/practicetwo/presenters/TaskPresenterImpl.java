package com.example.practicetwo.presenters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.practicetwo.R;
import com.example.practicetwo.TaskContract;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.loader.MainLoader;
import com.example.practicetwo.providers.StorageProvider;
import com.example.practicetwo.util.StorageFactory;

import java.util.List;

import static com.example.practicetwo.util.Constants.MODE;
import static com.example.practicetwo.util.Constants.TASK;

public class TaskPresenterImpl
        implements TaskContract.TaskPresenter,
        LoaderManager.LoaderCallbacks<List<Task>> {

    private Context context;
    private StorageProvider storageProvider;
    private TaskContract.TaskView taskView;
    private final boolean isFavouriteTasks;
    private LoaderManager loaderManager;
    private Bundle loaderBundle;

    public TaskPresenterImpl(View view,
                             TaskContract.TaskView taskView,
                             boolean isFavouriteTasks,
                             LoaderManager loaderManager) {
        this.taskView = taskView;
        this.context = view.getContext();
        this.storageProvider = StorageFactory.getInstance().getFactory(context);
        this.isFavouriteTasks = isFavouriteTasks;
        this.loaderManager = loaderManager;
        loaderManager.initLoader(R.integer.MAIN_LOADER, Bundle.EMPTY, this);
        loaderBundle = new Bundle();
    }

    @Override
    public void refreshData() {
        loaderManager.restartLoader(R.integer.MAIN_LOADER, Bundle.EMPTY, this);
    }

    @Override
    public void insertTask(Task task) {
        loaderBundle.clear();
        loaderBundle.putInt(MODE, R.integer.INSERT);
        loaderBundle.putParcelable(TASK, task);
        loaderManager.restartLoader(R.integer.MAIN_LOADER, loaderBundle, this);
    }

    @Override
    public void updateTask(Task task) {
        loaderBundle.clear();
        loaderBundle.putInt(MODE, R.integer.UPDATE);
        loaderBundle.putParcelable(TASK, task);
        loaderManager.restartLoader(R.integer.MAIN_LOADER, loaderBundle, this);
    }

    @Override
    public void showEditActivity(Task task) {
        taskView.showActivityToEditTask(task);
    }

    @Override
    public void changeFavourite(String taskId) {
        loaderBundle.clear();
        loaderBundle.putInt(MODE, R.integer.UPDATE_FAVOURITE);
        loaderBundle.putString(TASK, taskId);
        loaderManager.restartLoader(R.integer.MAIN_LOADER, loaderBundle, this);
    }

    @Override
    public void deleteTask(String taskId) {
        loaderBundle.clear();
        loaderBundle.putInt(MODE, R.integer.DELETE);
        loaderBundle.putString(TASK, taskId);
        loaderManager.restartLoader(R.integer.MAIN_LOADER, loaderBundle, this);
    }

    @NonNull
    @Override
    public Loader<List<Task>> onCreateLoader(int id, @Nullable Bundle args) {
        if (args == null) args = Bundle.EMPTY;
        return new MainLoader(context, storageProvider, args, isFavouriteTasks);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Task>> loader, List<Task> data) {
        taskView.getAdapter().setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Task>> loader) {
        Log.d("TAG", "onLoaderReset: ");
    }
}
