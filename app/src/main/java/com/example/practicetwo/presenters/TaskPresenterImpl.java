package com.example.practicetwo.presenters;

import android.content.Context;
import android.os.AsyncTask;
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

import java.util.List;

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
        loaderManager.restartLoader(R.integer.GET_LOADER, Bundle.EMPTY, this).forceLoad();
        loaderBundle = new Bundle();
    }

    @Override
    public void refresh() {
        loaderManager.restartLoader(R.integer.GET_LOADER, Bundle.EMPTY, this).forceLoad();
        Log.d("TAG", "refresh: ");
    }

    @Override
    public void notifyAdapter() {
        Log.d("TAG", "notifyAdapter: ");
        taskView.updateView();
    }

    @Override
    public void insertTask(Task task) {
        Log.d("TAG", "insertTask: ");
        loaderBundle.clear();
        loaderBundle.putParcelable(Constants.TASK, task);
        loaderManager.restartLoader(R.integer.INSERT_LOADER, loaderBundle, this).forceLoad();
    }

    @Override
    public void updateTask(Task task) {
        loaderBundle.clear();
        loaderBundle.putParcelable(Constants.TASK, task);
        loaderManager.restartLoader(R.integer.UPDATE_LOADER, loaderBundle, this).forceLoad();
    }

    @Override
    public void showEditActivity(Task task) {
        taskView.showActivityToEditTask(task);
    }

    @Override
    public void changeFavourite(String taskId) {
        Log.d("TAG", "changeFavourite: ");
        loaderBundle.clear();
        loaderBundle.putString(Constants.TASK, taskId);
        loaderManager.restartLoader(R.integer.UPDATE_FAVOURITE_LOADER, loaderBundle, this).forceLoad();
    }

    @Override
    public void deleteTask(String taskId) {
        Log.d("TAG", "deleteTask: ");
        loaderBundle.clear();
        loaderBundle.putString(Constants.TASK, taskId);
        loaderManager.restartLoader(R.integer.DELETE_LOADER, loaderBundle, this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<List<Task>> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case R.integer.INSERT_LOADER:
                Log.d("TAG", "onCreateLoader: insert");
                return new InsertTaskLoader(context, storageProvider, args, isFavouriteTasks);
            case R.integer.DELETE_LOADER:
                Log.d("TAG", "onCreateLoader: delete");
                return new DeleteTaskLoader(context, storageProvider, args, isFavouriteTasks);
            case R.integer.UPDATE_LOADER:
                Log.d("TAG", "onCreateLoader: update");
                return new UpdateTaskLoader(context, storageProvider, args, isFavouriteTasks);
            case R.integer.UPDATE_FAVOURITE_LOADER:
                Log.d("TAG", "onCreateLoader: fav");
                return new UpdateFavouriteTaskLoader(context, storageProvider, args, isFavouriteTasks);
            default:
                return new GetTaskLoader(context, storageProvider, isFavouriteTasks);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Task>> loader, List<Task> data) {
        taskView.getAdapter().setData(data);
        notifyAdapter();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Task>> loader) {
    }
}
