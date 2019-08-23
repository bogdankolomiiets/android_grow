package com.example.practicetwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.main.MainContract;
import com.example.practicetwo.main.MainPresenter;
import com.example.practicetwo.providers.StorageProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import static android.app.Activity.RESULT_OK;
import static com.example.practicetwo.Constants.TASK_EXTRA;

public class TaskFragment extends Fragment implements View.OnClickListener, MainContract.View {
    private MainContract.Presenter presenter;
    private RecyclerView.Adapter adapter;
    private RecyclerView taskRecyclerView;
    private View view;
    private boolean showFavouriteTasks;

    public TaskFragment(){}

    public TaskFragment(boolean showFavouriteTasks) {
        super();
        this.showFavouriteTasks = showFavouriteTasks;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.task_fragment, container, false);
        StorageProvider storageProvider = StorageFactory.getInstance().getFactory(view.getContext());
        storageProvider.addCallBackViewListener(this);
        taskRecyclerView = view.findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        presenter = new MainPresenter(this, storageProvider);
        if (showFavouriteTasks) {
            presenter.getFavouriteTasks();
        } else {
            presenter.getAllTasks();
        }
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
        adapter = new CustomRecyclerView(view.getContext(), presenter, tasks);
        taskRecyclerView.setAdapter(adapter);
    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showActivityToEditTask(Task task) {
        Intent intent = new Intent(getContext(), TaskActivity.class);
        intent.putExtra(TASK_EXTRA, task);
        startActivityForResult(intent, RequestCodes.EDIT_TASK_INTENT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCodes.NEW_TASK_INTENT_CODE) {
            if (resultCode == RESULT_OK) {
                presenter.addTask(data.getParcelableExtra(TASK_EXTRA));
            }
        } else if (requestCode == RequestCodes.EDIT_TASK_INTENT_CODE){
            if (resultCode == RESULT_OK){
                presenter.editTask(data.getParcelableExtra(TASK_EXTRA));
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.newTaskFab) {
            startActivityForResult(new Intent(getContext(), TaskActivity.class), RequestCodes.NEW_TASK_INTENT_CODE);
        }
    }
}
