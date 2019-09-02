package com.example.practicetwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.practicetwo.entity.Task;
import com.example.practicetwo.views.TaskFragment;

import static com.example.practicetwo.util.Constants.*;

public class TaskActivity extends AppCompatActivity {
    private EditText taskTitle;
    private EditText taskDescription;
    private Task task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task);

        //init UI elements
        initElements();

        task = getIntent().getParcelableExtra(TASK_EXTRA);
        if (task != null){
            taskTitle.setText(task.getTitle());
            taskDescription.setText(task.getDescription());
        }
    }

    private void initElements() {
        taskTitle = findViewById(R.id.taskTitle);
        taskDescription = findViewById(R.id.taskDescription);

        //find and set up toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout_task, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.saveItem) {
            saveTask();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTask() {
        String title = taskTitle.getText().toString();
        String description = taskDescription.getText().toString();
        if (title.length() > 0 && description.length() > 0) {
            if (task == null) {
                task = new Task();
            }
            task.setTitle(title);
            task.setDescription(description);

            Intent intent = new Intent(this, TaskFragment.class);
            intent.putExtra(TASK_EXTRA, (Parcelable) task);
            setResult(RESULT_OK, intent);
            finish();
        } else Toast.makeText(this, R.string.fieldIsEmpty, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        taskTitle.setText(savedInstanceState.getString(TASK_TITLE));
        taskDescription.setText(savedInstanceState.getString(TASK_DESCRIPTION));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TASK_TITLE, taskTitle.getText().toString());
        outState.putString(TASK_DESCRIPTION, taskDescription.getText().toString());
    }
}
