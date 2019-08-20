package com.example.practicetwo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FavouriteTaskFragment extends Fragment {
    private static FavouriteTaskFragment favouriteTaskFragmentInstance;

    private FavouriteTaskFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favourite_task_fragment, null);
    }

    public static FavouriteTaskFragment getInstance() {
        if (favouriteTaskFragmentInstance == null) {
            favouriteTaskFragmentInstance = new FavouriteTaskFragment();
        }
        return favouriteTaskFragmentInstance;
    }
}
