package com.example.practicetwo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.practicetwo.util.VisibleFragmentUpdater;
import com.example.practicetwo.views.TaskFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private List<TaskFragment> fragments;
    private int[] tabTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        initTabTitles();
        initFragments();

        //init UI components
        initComponents();
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new TaskFragment(false));
        fragments.add(new TaskFragment(true));
    }


    private void initTabTitles() {
        tabTitles = new int[] {R.string.taskAllText, R.string.taskFavouriteText};
    }

    private void initComponents() {
        initDrawer();

        //init viewPager2 and tabLayout
        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                VisibleFragmentUpdater updater = (VisibleFragmentUpdater) pageAdapter.getFragment(position);
                Log.d("TAG", "onPageSelected: ");
                updater.updateFragment();
            }
        });

        viewPager2.setAdapter(pageAdapter);
        TabLayoutMediator layoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(tabTitles[position]));
        layoutMediator.attach();
    }

    private void initDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        //init toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.tasksDrawerItem:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.settingsDrawerItem:
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
