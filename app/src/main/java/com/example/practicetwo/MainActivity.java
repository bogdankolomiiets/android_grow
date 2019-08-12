package com.example.practicetwo;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        Toast.makeText(this, "onCreate - " + getLifecycle().getCurrentState().name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        Toast.makeText(this, "onStart - " + getLifecycle().getCurrentState().name(), Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    @Override
    protected void onResume() {
        Toast.makeText(this, "onResume - " + getLifecycle().getCurrentState().name(), Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Toast.makeText(this, "onPause - " + getLifecycle().getCurrentState().name(), Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Toast.makeText(this, "onStop - " + getLifecycle().getCurrentState().name(), Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Toast.makeText(this, "onRestart - " + getLifecycle().getCurrentState().name(), Toast.LENGTH_SHORT).show();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "onDestroy - " + getLifecycle().getCurrentState().name(), Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
