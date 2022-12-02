package com.example.lendti.UserIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.lendti.Adapter.EquipoITAdapter;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaEquipoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EquipoITAdapter equipoITAdapter;
    FirebaseFirestore firebaseFirestore;
    FloatingActionButton floatadd;
    BottomNavigationView bottomNavigationView;
    List<String> listaUltimos = new ArrayList<>();
    FirestoreRecyclerOptions<Equipo> firestoreRecyclerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_equipo);


        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycleViewSingle);
        floatadd = findViewById(R.id.floatingAgregarEquipo);

        String lista = getIntent().getStringExtra("lista");
        String main = getIntent().getStringExtra("main");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(lista==null || lista.equals("")){
            floatadd.setVisibility(View.GONE);
            firebaseFirestore.collection("equipos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    listaUltimos.add(document.getId());
                                }
                            } else {
                                //Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
            if(listaUltimos.size()<=5){
                Query query = firebaseFirestore.collection("equipos");
                firestoreRecyclerOptions =
                        new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query,Equipo.class).build();
            }else{
                Query query = firebaseFirestore.collection("equipos").endBefore(listaUltimos.get((listaUltimos.size()-1))).limit(5);
                firestoreRecyclerOptions =
                        new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query,Equipo.class).build();

            }
        }else{
            Query query = firebaseFirestore.collection("equipos");
            firestoreRecyclerOptions =
                    new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query,Equipo.class).build();
        }
        equipoITAdapter = new EquipoITAdapter(firestoreRecyclerOptions,this);
        equipoITAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(equipoITAdapter);


        setBottomNavigationView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        equipoITAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        equipoITAdapter.stopListening();
    }

    public void agregarEquipo(View view){
        startActivity(new Intent(ListaEquipoActivity.this,AgregarEquipoActivity.class));
        finish();
    }

    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationGestionUserIT);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.page_gestion);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_inicio:
                        Intent i  = new Intent(ListaEquipoActivity.this,ListaEquipoActivity.class);
                        i.putExtra("main","main");
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_gestion:
                        return true;
                    case R.id.page_solicitudes:
                        startActivity(new Intent(ListaEquipoActivity.this,SolicitudActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_perfil:
                        startActivity(new Intent(ListaEquipoActivity.this,PerfilActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

}