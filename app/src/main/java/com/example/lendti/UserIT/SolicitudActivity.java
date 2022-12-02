package com.example.lendti.UserIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lendti.Adapter.SolicitudITAdapter;
import com.example.lendti.Entity.Solicitud;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SolicitudActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    SolicitudITAdapter solicitudITAdapter;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);
        setBottomNavigationView();

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycleViewSolicitudUserIT);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = firebaseFirestore.collection("solicitudes");

        FirestoreRecyclerOptions<Solicitud> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Solicitud>().setQuery(query, Solicitud.class).build();

        solicitudITAdapter = new SolicitudITAdapter(firestoreRecyclerOptions,this);
        solicitudITAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(solicitudITAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        solicitudITAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        solicitudITAdapter.stopListening();
    }




    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationSolicitudUserIT);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.page_solicitudes);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_inicio:
                        Intent i  = new Intent(SolicitudActivity.this,ListaEquipoActivity.class);
                        i.putExtra("main","main");
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_gestion:
                        Intent i1  = new Intent(SolicitudActivity.this,ListaEquipoActivity.class);
                        i1.putExtra("lista","lista");
                        startActivity(new Intent(i1));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_solicitudes:
                        return true;
                    case R.id.page_perfil:
                        startActivity(new Intent(SolicitudActivity.this,PerfilActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
}