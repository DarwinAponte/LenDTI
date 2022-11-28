package com.example.lendti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearSnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lendti.UserIT.ListaEquipoActivity;

public class LogueoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueo);

        Button ingresar = findViewById(R.id.buttonAcceder);
        TextView registrar = findViewById(R.id.clickRegister);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogueoActivity.this,RegisterActivity.class);
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




    }




}