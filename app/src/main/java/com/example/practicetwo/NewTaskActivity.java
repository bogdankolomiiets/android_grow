package com.example.practicetwo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicetwo.providers.DatabaseProviderImpl;
import com.example.practicetwo.providers.ExternalStorageProviderImpl;
import com.example.practicetwo.providers.InternalStorageProviderImpl;
import com.example.practicetwo.providers.SharedPreferencesProviderImpl;
import com.example.practicetwo.providers.StorageProvider;

import static com.example.practicetwo.Constants.*;

public class NewTaskActivity extends AppCompatActivity {
    private EditText newTaskTitle;
    private EditText newTaskDescription;
    private StorageProvider storageProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_task);

        //init UI elements
        initElements();
    }

    private void initElements() {
        newTaskTitle = findViewById(R.id.newTaskTitle);
        newTaskDescription = findViewById(R.id.newTaskDescription);

        //find and set up toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveItem:
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null){
            newTaskTitle.setText(savedInstanceState.getString(NEW_TASK_TITLE));
            newTaskDescription.setText(savedInstanceState.getString(NEW_TASK_DESCRIPTION));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NEW_TASK_TITLE, newTaskTitle.getText().toString());
        outState.putString(NEW_TASK_DESCRIPTION, newTaskDescription.getText().toString());
    }
}
