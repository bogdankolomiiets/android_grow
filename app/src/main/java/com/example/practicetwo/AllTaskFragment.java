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

import java.util.List;

public class AllTaskFragment extends Fragment implements MainContract.View {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.all_task_fragment, container, false);
        new MainPresenter(this, StorageFactory.getInstance().getFactory(view.getContext())).getAllTasks();
        return view;
    }

    @Override
    public void showTasks(List<Task> tasks) {
        RecyclerView allTaskRecyclerView = view.findViewById(R.id.allTaskRecyclerView);
        allTaskRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        allTaskRecyclerView.setAdapter(new CustomRecyclerView(view.getContext(), tasks));
    }
}
