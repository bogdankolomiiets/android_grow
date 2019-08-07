package com.example.practiceone;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Main_activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
    }


    public void calculate(View view){
        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
    }
}
