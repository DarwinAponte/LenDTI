package com.example.lendti.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lendti.R;

public class ListaDispositivosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_dispositivos);
    }

    public void vista1 (View view){
        Intent intent = new Intent(this, SolicitudDevicesActivity.class);
        startActivity(intent);
    }
}