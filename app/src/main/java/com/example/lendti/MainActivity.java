package com.example.lendti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

    }
    public void cargando(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void terminarCargando(){
        progressBar.setVisibility(View.GONE);
    }
}