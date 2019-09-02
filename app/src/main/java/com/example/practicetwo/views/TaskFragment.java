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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicetwo.CustomRecyclerView;
import com.example.practicetwo.R;
import com.example.practicetwo.TaskActivity;
import com.example.practicetwo.TaskContract;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.presenters.TaskPresenterImpl;
import com.example.practicetwo.util.Constants;
import com.example.practicetwo.util.VisibleFragmentDataUpdater;
import com.example.practicetwo.util.RequestCodes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.example.practicetwo.util.Constants.TASK_EXTRA;
import static com.example.practicetwo.util.RequestCodes.NEW_TASK_INTENT_CODE;

public class TaskFragment extends Fragment
                          implements TaskContract.TaskView,
        VisibleFragmentDataUpdater {

    private TaskContract.TaskPresenter taskPresenter;
    private CustomRecyclerView adapter;
    private boolean isFavouriteTasks;

    public TaskFragment() {
    }

    public TaskFragment(boolean isFavouriteTasks) {
        super();
        this.isFavouriteTasks = isFavouriteTasks;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);

        if (savedInstanceState != null){
            isFavouriteTasks = savedInstanceState.getBoolean(Constants.FAVOURITE_TASK);
        }

        taskPresenter = new TaskPresenterImpl(view, this, isFavouriteTasks, getLoaderManager());
        adapter = new CustomRecyclerView(this.getContext(), taskPresenter, new ArrayList<>());
        RecyclerView taskRecyclerView = view.findViewById(R.id.taskRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        taskRecyclerView.setLayoutManager(layoutManager);
        taskRecyclerView.setAdapter(adapter);

        //setup divider
        DividerItemDecoration divider = new DividerItemDecoration(view.getContext(), layoutManager.getOrientation());
        taskRecyclerView.addItemDecoration(divider);

        //init FloatingActionButton
        FloatingActionButton newTaskFab = view.findViewById(R.id.newTaskFab);
        newTaskFab.setOnClickListener(view1 -> startActivityForResult(new Intent(view1.getContext(), TaskActivity.class), NEW_TASK_INTENT_CODE));

        return view;
    }

    @Override
    public CustomRecyclerView getAdapter() {
        return adapter;
    }

    @Override
    public void updateFragment() {
        if (taskPresenter != null)
        taskPresenter.refreshData();
    }

    @Override
    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showActivityToEditTask(Task task) {
        Intent intent = new Intent(getContext(), TaskActivity.class);
        intent.putExtra(TASK_EXTRA, (Parcelable) task);
        startActivityForResult(intent, RequestCodes.EDIT_TASK_INTENT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Task tempTask = data.getParcelableExtra(TASK_EXTRA);
        if (tempTask != null) {
            if (requestCode == RequestCodes.NEW_TASK_INTENT_CODE) {
                if (resultCode == RESULT_OK) {
                    taskPresenter.insertTask(tempTask);
                }
            } else if (requestCode == RequestCodes.EDIT_TASK_INTENT_CODE) {
                if (resultCode == RESULT_OK) {
                    taskPresenter.updateTask(tempTask);
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constants.FAVOURITE_TASK, isFavouriteTasks);
    }
}
