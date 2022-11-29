package com.example.lendti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lendti.Admin.AdminActivity;
import com.example.lendti.Client.ListaClienteActivity;
import com.example.lendti.UserIT.ListaEquipoActivity;

public class LogueoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueo);

        Button ingresar = findViewById(R.id.buttonAcceder);
        TextView registrar = findViewById(R.id.clickRegister);
        Button cliente =findViewById(R.id.buttonCliente);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogueoActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogueoActivity.this, ListaEquipoActivity.class));
                finish();
            }
        });

        cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogueoActivity.this, ListaClienteActivity.class));
                finish();
            }
        });




    }





}