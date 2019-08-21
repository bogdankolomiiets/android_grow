package com.example.practicetwo;

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
import java.util.List;

public class AllTaskFragment extends Fragment implements MainContract.View {
    private StorageProvider storageProvider;
    private List<Task> allTaskList;
    private View view;

    public AllTaskFragment(){}

    public AllTaskFragment(StorageProvider storageProvider){
        super();
        this.storageProvider = storageProvider;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPresenter mainPresenter = new MainPresenter(storageProvider);

        //getting list of tasks
        allTaskList = mainPresenter.getTaskFromStorage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.all_task_fragment, container, false);
        showTasks();
        return view;
    }

    @Override
    public void showTasks() {
        RecyclerView allTaskRecyclerView = view.findViewById(R.id.allTaskRecyclerView);
        allTaskRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        allTaskRecyclerView.setAdapter(new CustomRecyclerView(view.getContext(), allTaskList));
    }
}
