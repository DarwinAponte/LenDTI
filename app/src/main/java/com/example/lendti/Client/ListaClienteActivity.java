package com.example.lendti.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lendti.R;

public class ListaClienteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_lista);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAprobadas);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListaClienteActivity.this));

    }

    public void otraVista(View view){
        Intent intent = new Intent(this,ListaDispositivosActivity.class);
        startActivity(intent);


    }
}