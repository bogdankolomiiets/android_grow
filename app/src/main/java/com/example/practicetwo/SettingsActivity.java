package com.example.practicetwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private RadioButton sharedPreferencesRadio;
    private RadioButton internalStorageRadio;
    private RadioButton externalStorageRadio;
    private RadioButton sqlDatabaseRadio;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);

        //find and set up toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        initRadioButtons();
        initDrawer();
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

    private void initRadioButtons() {
        sharedPreferencesRadio = findViewById(R.id.sharedPreferencesRadio);
        sharedPreferencesRadio.setOnClickListener(this);

        internalStorageRadio = findViewById(R.id.internalStorageRadio);
        internalStorageRadio.setOnClickListener(this);

        externalStorageRadio = findViewById(R.id.externalStorageRadio);
        externalStorageRadio.setOnClickListener(this);

        sqlDatabaseRadio = findViewById(R.id.sqlDatabaseRadio);
        sqlDatabaseRadio.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sharedPreferencesRadio:
                break;
            case R.id.internalStorageRadio:
                break;
            case R.id.externalStorageRadio:
                ExternalStoragePermissionChecker.check(this,this);
                break;
            case R.id.sqlDatabaseRadio:
                break;
        }
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
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.settingsDrawerItem:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
