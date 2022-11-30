package com.example.lendti.UserIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lendti.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PerfilActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setBottomNavigationView();
    }


    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationPerfilUserIT);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.page_perfil);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_inicio:
                        startActivity(new Intent(PerfilActivity.this,MainActivityUserIT.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_gestion:
                        startActivity(new Intent(PerfilActivity.this,ListaEquipoActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_solicitudes:
                        startActivity(new Intent(PerfilActivity.this,SolicitudActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_perfil:

                        return true;
                }
                return false;
            }
        });
    }
}