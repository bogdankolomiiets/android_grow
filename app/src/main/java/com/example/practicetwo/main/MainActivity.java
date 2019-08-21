package com.example.practicetwo.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import com.example.practicetwo.NewTaskActivity;
import com.example.practicetwo.PageAdapter;
import com.example.practicetwo.R;
import com.example.practicetwo.RequestCodes;
import com.example.practicetwo.SettingsActivity;
import com.example.practicetwo.database.TaskDatabase;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.providers.DatabaseProviderImpl;
import com.example.practicetwo.providers.ExternalStorageProviderImpl;
import com.example.practicetwo.providers.InternalStorageProviderImpl;
import com.example.practicetwo.providers.SharedPreferencesProviderImpl;
import com.example.practicetwo.providers.StorageProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import static com.example.practicetwo.Constants.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private StorageProvider storageProvider;
    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        //init data storage provider
        initStorageProvider();
        presenter = new MainPresenter(storageProvider);

        //init UI components
        initComponents();
    }

    private void initComponents() {
        initDrawer();

        //init FloatingActionButton
        FloatingActionButton newTaskFab = findViewById(R.id.newTaskFab);
        newTaskFab.setOnClickListener(this);

        //init viewPager and tabLayout
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        PageAdapter pageAdapter = new PageAdapter(this, storageProvider, getSupportFragmentManager(), 2);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCodes.NEW_TASK_INTENT_CODE){
            if (resultCode == RESULT_OK){
                Task task = data.getParcelableExtra(TASK);
                presenter.saveTask(task);
            }
        }
    }

    @Override
    protected void onDestroy() {
        TaskDatabase.destroyInstance();
        super.onDestroy();
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.newTaskFab) {
            startActivityForResult(new Intent(this, NewTaskActivity.class), RequestCodes.NEW_TASK_INTENT_CODE);
        }
    }

    private void initStorageProvider() {
        String provider = getSharedPreferences(SHARE_PREFERENCES_FILE_NAME, MODE_PRIVATE).getString(STORAGE_PROVIDER, TAG_INTERNAL);
        switch (provider) {
            case TAG_SHARED:
                storageProvider = new SharedPreferencesProviderImpl(this);
                break;
            case TAG_INTERNAL:
                storageProvider = new InternalStorageProviderImpl(this);
                break;
            case TAG_EXTERNAL:
                storageProvider = new ExternalStorageProviderImpl(this);
                break;
            case TAG_DATABASE:
                storageProvider = new DatabaseProviderImpl(this);
                break;
        }
    }
}
