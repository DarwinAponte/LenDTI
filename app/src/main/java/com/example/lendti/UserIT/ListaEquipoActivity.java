package com.example.lendti.UserIT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lendti.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenuView;

public class ListaEquipoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_equipo);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.page_inicio:
                    startActivity(new Intent(ListaEquipoActivity.this,ListaEquipoActivity.class));
                    break;
                case R.id.page_gestion:
                    startActivity(new Intent(ListaEquipoActivity.this,AgregarEquipoActivity.class));
                    break;
                case R.id.page_solicitudes:
                    startActivity(new Intent(ListaEquipoActivity.this,SolicitudActivity.class));
                case R.id.page_perfil:
                    startActivity(new Intent(ListaEquipoActivity.this,PerfilActivity.class));
                default:
                    break;
            }
            return true;
        });


    }

}