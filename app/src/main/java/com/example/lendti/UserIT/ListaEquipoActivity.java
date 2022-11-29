package com.example.lendti.UserIT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lendti.Adapter.EquipoAdapter;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenuView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListaEquipoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EquipoAdapter equipoAdapter;
    FirebaseFirestore firebaseFirestore;
    FloatingActionButton floatadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_equipo);


        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycleViewSingle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = firebaseFirestore.collection("equipos");

        FirestoreRecyclerOptions<Equipo> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query,Equipo.class).build();

        equipoAdapter = new EquipoAdapter(firestoreRecyclerOptions,this);
        equipoAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(equipoAdapter);

        floatadd = findViewById(R.id.floatingAgregarEquipo);
        floatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaEquipoActivity.this,AgregarEquipoActivity.class));
                finish();
            }
        });

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

    @Override
    protected void onStart() {
        super.onStart();
        equipoAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        equipoAdapter.stopListening();
    }

}