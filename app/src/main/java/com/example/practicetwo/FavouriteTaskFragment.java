package com.example.practicetwo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicetwo.entity.Task;
import com.example.practicetwo.main.MainContract;
import com.example.practicetwo.main.MainPresenter;
import com.example.practicetwo.providers.InternalStorageProviderImpl;
import com.example.practicetwo.providers.StorageProvider;

import java.util.List;

public class FavouriteTaskFragment extends Fragment implements MainContract.View {
    private StorageProvider storageProvider;
    private List<Task> favouriteTaskList;
    private View view;

    public FavouriteTaskFragment() {
    }

    public FavouriteTaskFragment(StorageProvider storageProvider) {
        this.storageProvider = storageProvider;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPresenter mainPresenter = new MainPresenter(storageProvider);

        //getting favourite list of tasks
        favouriteTaskList = mainPresenter.getFavouriteTaskFromStorage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favourite_task_fragment, container, false);
        showTasks();
        return view;
    }

    @Override
    public void showTasks() {
        RecyclerView favouriteTaskRecyclerView = view.findViewById(R.id.favouriteTaskRecyclerView);
        favouriteTaskRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        favouriteTaskRecyclerView.setAdapter(new CustomRecyclerView(view.getContext(), favouriteTaskList));
    }
}
