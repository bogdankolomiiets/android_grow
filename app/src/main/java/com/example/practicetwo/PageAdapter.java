package com.example.practicetwo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.practicetwo.views.TaskFragment;

import java.util.List;


public class PageAdapter extends FragmentStateAdapter {
    private List<TaskFragment> fragments;

    public PageAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, List<TaskFragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    public Fragment getFragment(int position){
        return fragments.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }


}
