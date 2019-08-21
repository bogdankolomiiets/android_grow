package com.example.practicetwo;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.practicetwo.providers.StorageProvider;

public class PageAdapter extends FragmentPagerAdapter {
    private StorageProvider storageProvider;
    private int numOfTabs;
    private Context context;

    public PageAdapter(Context context, StorageProvider storageProvider, FragmentManager fm, int numOfTabs) {
        super(fm);
        this.storageProvider = storageProvider;
        this.context = context;
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllTaskFragment(storageProvider);
            case 1:
                return new FavouriteTaskFragment(storageProvider);
            default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.taskAllText);
            case 1:
                return context.getString(R.string.taskFavouriteText);
            default:
                return null;
        }
    }
}
