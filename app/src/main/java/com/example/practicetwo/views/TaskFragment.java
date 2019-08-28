package com.example.practicetwo.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.practicetwo.CustomRecyclerView;
import com.example.practicetwo.R;
import com.example.practicetwo.util.Constants;
import com.example.practicetwo.util.RequestCodes;
import com.example.practicetwo.TaskActivity;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.TaskContract;
import com.example.practicetwo.presenters.TaskPresenterImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.example.practicetwo.util.Constants.TASK_EXTRA;

public class TaskFragment extends Fragment implements View.OnClickListener, TaskContract.TaskView {
    private TaskContract.TaskPresenter taskPresenter;
    private View view;
    private CustomRecyclerView adapter;
    private boolean showFavouriteTasks;

    public TaskFragment() {
    }

    public TaskFragment(boolean showFavouriteTasks) {
        super();
        this.showFavouriteTasks = showFavouriteTasks;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            showFavouriteTasks = savedInstanceState.getBoolean(Constants.FAVOURITE_TASK);
        }
        view = inflater.inflate(R.layout.task_fragment, container, false);
        taskPresenter = new TaskPresenterImpl(view, this, showFavouriteTasks, getLoaderManager());

        adapter = new CustomRecyclerView(this.getContext(), taskPresenter, new ArrayList<>());
        RecyclerView taskRecyclerView = view.findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        taskRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //init FloatingActionButton
        FloatingActionButton newTaskFab = view.getRootView().findViewById(R.id.newTaskFab);
        newTaskFab.setOnClickListener(this);

        //addOnPageChangeListener for viewPager
        ViewPager viewPager = view.getRootView().findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                taskPresenter.refresh();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public CustomRecyclerView getAdapter() {
        return adapter;
    }

    @Override
    public void updateView() {
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
        if (requestCode == RequestCodes.NEW_TASK_INTENT_CODE) {
            if (resultCode == RESULT_OK) {
                taskPresenter.insertTask(data.getParcelableExtra(TASK_EXTRA));
            }
        } else if (requestCode == RequestCodes.EDIT_TASK_INTENT_CODE) {
            if (resultCode == RESULT_OK) {
                taskPresenter.updateTask(data.getParcelableExtra(TASK_EXTRA));
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
}
