package com.example.practicetwo;

import android.os.Bundle;
import android.util.Log;
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

public class FavouriteTaskFragment extends Fragment implements MainContract.View {
    private View view;
    private MainContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favourite_task_fragment, container, false);
        StorageProvider storageProvider = StorageFactory.getInstance().getFactory(view.getContext());
        storageProvider.addCallBackViewListener(this);
        presenter = new MainPresenter(this, storageProvider);
        presenter.getFavouriteTasks();
        return view;
    }

    @Override
    public void showTasks(List<Task> tasks) {
        RecyclerView favouriteTaskRecyclerView = view.findViewById(R.id.favouriteTaskRecyclerView);
        favouriteTaskRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        favouriteTaskRecyclerView.setAdapter(new CustomRecyclerView(view.getContext(), presenter, tasks));
    }

    @Override
    public void refresh() {
        presenter.getFavouriteTasks();
    }
}
