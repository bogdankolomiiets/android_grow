package com.example.practicetwo.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.practicetwo.R;
import com.example.practicetwo.util.Constants;
import com.example.practicetwo.util.RequestCodes;
import com.example.practicetwo.TaskActivity;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.TaskContract;
import com.example.practicetwo.presenters.TaskPresenterImpl;
import com.example.practicetwo.util.TaskLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.practicetwo.util.Constants.TASK_EXTRA;

public class TaskFragment extends Fragment implements View.OnClickListener, TaskContract.TaskView, LoaderManager.LoaderCallbacks<String> {
    private TaskContract.TaskPresenter taskPresenter;
    private RecyclerView taskRecyclerView;
    private View view;
    private boolean showFavouriteTasks;
    private Loader<String> loader;

    public TaskFragment(){}

    public TaskFragment(boolean showFavouriteTasks) {
        super();
        this.showFavouriteTasks = showFavouriteTasks;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null){
            showFavouriteTasks = savedInstanceState.getBoolean(Constants.FAVOURITE_TASK);
        }

        loader = getLoaderManager().initLoader(R.integer.LOADER_ID, savedInstanceState, this);

        view = inflater.inflate(R.layout.task_fragment, container, false);
        taskRecyclerView = view.findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        taskPresenter = new TaskPresenterImpl(view, this, showFavouriteTasks);
        taskPresenter.getTasks();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //init FloatingActionButton
        FloatingActionButton newTaskFab = view.getRootView().findViewById(R.id.newTaskFab);
        newTaskFab.setOnClickListener(this);
    }

    @Override
    public void showTasks(List<Task> tasks) {
        taskRecyclerView.setAdapter(taskPresenter.getAdapter());
    }

    @Override
    public void showActivityToEditTask(Task task) {
        Intent intent = new Intent(getContext(), TaskActivity.class);
        intent.putExtra(TASK_EXTRA, (Parcelable) task);
        startActivityForResult(intent, RequestCodes.EDIT_TASK_INTENT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCodes.NEW_TASK_INTENT_CODE) {
            if (resultCode == RESULT_OK) {
                taskPresenter.addTask(data.getParcelableExtra(TASK_EXTRA));
            }
        } else if (requestCode == RequestCodes.EDIT_TASK_INTENT_CODE){
            if (resultCode == RESULT_OK){
                taskPresenter.editTask(data.getParcelableExtra(TASK_EXTRA));
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.newTaskFab) {
            startActivityForResult(new Intent(getContext(), TaskActivity.class), RequestCodes.NEW_TASK_INTENT_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constants.FAVOURITE_TASK, showFavouriteTasks);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        taskPresenter.removeCallBackViewListener();

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new TaskLoader(this.getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        taskPresenter.refresh();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
