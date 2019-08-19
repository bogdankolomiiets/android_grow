package com.example.practicetwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import static com.example.practicetwo.Constants.*;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private RadioButton sharedPreferencesRadio;
    private RadioButton internalStorageRadio;
    private RadioButton externalStorageRadio;
    private RadioButton sqlDatabaseRadio;
    private RadioGroup storeTypeRadioGroup;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);

        //find and set up toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        initRadioButtons();
        initActiveRadioButton();
        initDrawer();
    }

    private void initRadioButtons() {
        //init radioGroup
        storeTypeRadioGroup = findViewById(R.id.storeTypeRadioGroup);

        sharedPreferencesRadio = findViewById(R.id.sharedPreferencesRadio);
        sharedPreferencesRadio.setTag(TAG_SHARED);
        sharedPreferencesRadio.setOnClickListener(this);

        internalStorageRadio = findViewById(R.id.internalStorageRadio);
        internalStorageRadio.setTag(TAG_INTERNAL);
        internalStorageRadio.setOnClickListener(this);

        externalStorageRadio = findViewById(R.id.externalStorageRadio);
        externalStorageRadio.setTag(TAG_EXTERNAL);
        externalStorageRadio.setOnClickListener(this);

        sqlDatabaseRadio = findViewById(R.id.sqlDatabaseRadio);
        sqlDatabaseRadio.setTag(TAG_DATABASE);
        sqlDatabaseRadio.setOnClickListener(this);
    }

    private void initActiveRadioButton() {
        String tag = getSharedPreferences(SHARE_PREFERENCES_FILE_NAME, MODE_PRIVATE).getString(STORAGE_PROVIDER, null);
        if (tag != null){
        storeTypeRadioGroup.check(storeTypeRadioGroup.findViewWithTag(tag).getId());
        }
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
    public void onClick(View view) {
        if (view.getId() == R.id.externalStorageRadio) {
            ExternalStoragePermissionChecker.check(this, this);
        }
    }

    private void putToSharedPreferences(){
        SharedPreferences.Editor editor = getSharedPreferences(SHARE_PREFERENCES_FILE_NAME, MODE_PRIVATE).edit();
        editor.putString(STORAGE_PROVIDER,
                storeTypeRadioGroup.findViewById(storeTypeRadioGroup.getCheckedRadioButtonId()).getTag().toString());
        editor.apply();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestCodes.STORAGE_PERMISSIONS_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, R.string.permissionGranted, Toast.LENGTH_SHORT).show();
            } else {
                externalStorageRadio.setChecked(false);
                internalStorageRadio.setChecked(true);
                Toast.makeText(this, R.string.permissionNotGranted, Toast.LENGTH_SHORT).show();
            }
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

    @Override
    protected void onPause() {
        super.onPause();
        putToSharedPreferences();
    }
}
