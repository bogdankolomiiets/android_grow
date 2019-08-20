package com.example.practicetwo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    int numOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AllTaskFragment.getInstance();
            case 1:
                return FavouriteTaskFragment.getInstance();
            default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
