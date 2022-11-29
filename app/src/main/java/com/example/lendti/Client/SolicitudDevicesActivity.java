package com.example.lendti.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lendti.R;

public class SolicitudDevicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_devices);
    }
    public void vista10 (View view){
        Intent intent = new Intent(this,ReservaDispositivosActivity.class);
        startActivity(intent);
    }

}