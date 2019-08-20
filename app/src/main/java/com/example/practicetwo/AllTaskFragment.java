package com.example.practicetwo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AllTaskFragment extends Fragment {
    private static AllTaskFragment allTaskFragmentInstance;
    private static Context context;

    private AllTaskFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        return inflater.inflate(R.layout.all_task_fragment, null);
    }

    public static AllTaskFragment getInstance(){
        if (allTaskFragmentInstance == null) {
            allTaskFragmentInstance = new AllTaskFragment();
        }
        Toast.makeText(context, "gf", Toast.LENGTH_SHORT).show();
        return allTaskFragmentInstance;
    }
}
