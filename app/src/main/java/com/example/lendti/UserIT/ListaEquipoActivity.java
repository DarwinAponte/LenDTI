package com.example.lendti.UserIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lendti.Adapter.EquipoITAdapter;
import com.example.lendti.Adapter.EquipoITGridAdapter;
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
    List<Equipo> listaUltimos = new ArrayList<>();
    FirestoreRecyclerOptions<Equipo> firestoreRecyclerOptions;
    ImageButton btnLista,btnGrid;
    EquipoITGridAdapter equipoITGridAdapter;
    Query query;
    TextView titulo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_equipo);


        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycleViewSingle);
        floatadd = findViewById(R.id.floatingAgregarEquipo);
        btnLista = findViewById(R.id.imageButtonLista);
        btnGrid = findViewById(R.id.imageButtonListaCuadrado);
        titulo = findViewById(R.id.titulo);
        bottomNavigationView = findViewById(R.id.bottomNavigationGestionUserIT);
        String lista = getIntent().getStringExtra("lista");


        recyclerView.setLayoutManager(new LinearLayoutManager(ListaEquipoActivity.this));

        System.out.println(lista);
        if(lista == null || lista.equals("")){
            bottomNavigationView.setSelectedItemId(R.id.page_inicio);
            titulo.setText("Los últimos 5 modificados");
            System.out.println("entro aqui");
            floatadd.setVisibility(View.GONE);
            firebaseFirestore.collection("equipos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            //Log.d(TAG, document.getId() + " => " + document.getData());
                            Equipo equipo = document.toObject(Equipo.class);
                            listaUltimos.add(equipo);
                        }
                    } else {
                        //Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
            if(listaUltimos.size()<=5){
                query = firebaseFirestore.collection("equipos");

            }else{
                query = firebaseFirestore.collection("equipos").orderBy("timestamp").limit(5);
            }
        }else {
            bottomNavigationView.setSelectedItemId(R.id.page_gestion);
            query = firebaseFirestore.collection("equipos");
        }
        firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query,Equipo.class).build();
        equipoITAdapter = new EquipoITAdapter(firestoreRecyclerOptions, ListaEquipoActivity.this);
        equipoITAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(equipoITAdapter);



        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.setLayoutManager(new LinearLayoutManager(ListaEquipoActivity.this));

                System.out.println(lista);
                if(lista == null || lista.equals("")){
                    bottomNavigationView.setSelectedItemId(R.id.page_inicio);
                    titulo.setText("Los últimos 5 modificados");
                    System.out.println("entro aqui");
                    floatadd.setVisibility(View.GONE);
                    firebaseFirestore.collection("equipos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    Equipo equipo = document.toObject(Equipo.class);
                                    listaUltimos.add(equipo);
                                }
                            } else {
                                //Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
                    if(listaUltimos.size()<=5){
                        query = firebaseFirestore.collection("equipos");

                    }else{
                        query = firebaseFirestore.collection("equipos").orderBy("timestamp", Query.Direction.DESCENDING).limit(5);
                    }
                }else{
                    bottomNavigationView.setSelectedItemId(R.id.page_gestion);
                    query = firebaseFirestore.collection("equipos");
                }
                firestoreRecyclerOptions =
                        new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query, Equipo.class).build();
                equipoITAdapter = new EquipoITAdapter(firestoreRecyclerOptions, ListaEquipoActivity.this);
                equipoITAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(equipoITAdapter);

                
            }
        });

        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("para grid");
                recyclerView.setLayoutManager(new GridLayoutManager(ListaEquipoActivity.this,2));
                if(lista == null || lista.equals("")){
                    bottomNavigationView.setSelectedItemId(R.id.page_inicio);
                    titulo.setText("Los últimos 5 agregados");
                    floatadd.setVisibility(View.GONE);
                    firebaseFirestore.collection("equipos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    Equipo equipo = document.toObject(Equipo.class);
                                    listaUltimos.add(equipo);
                                }
                            } else {
                                //Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
                    if(listaUltimos.size()<=5){
                        query = firebaseFirestore.collection("equipos");
                    }else{
                        query = firebaseFirestore.collection("equipos").endBefore(listaUltimos.get((listaUltimos.size()-1))).limit(5);
                    }
                }else{
                    System.out.println("obtuvo para grid");
                    bottomNavigationView.setSelectedItemId(R.id.page_gestion);
                    query = firebaseFirestore.collection("equipos");
                }
                firestoreRecyclerOptions =
                        new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query,Equipo.class).build();
                equipoITGridAdapter = new EquipoITGridAdapter(firestoreRecyclerOptions,ListaEquipoActivity.this);
                equipoITGridAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(equipoITGridAdapter);
                equipoITGridAdapter.startListening();
            }
        });
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

        bottomNavigationView.clearAnimation();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_inicio:
                        Intent i  = new Intent(ListaEquipoActivity.this,ListaEquipoActivity.class);
                        i.putExtra("lista","");
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_gestion:
                        Intent i1  = new Intent(ListaEquipoActivity.this,ListaEquipoActivity.class);
                        i1.putExtra("main","");
                        i1.putExtra("lista","lista");
                        startActivity(i1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_solicitudes:
                        startActivity(new Intent(ListaEquipoActivity.this,SolicitudActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_perfil:
                        startActivity(new Intent(ListaEquipoActivity.this, PerfilTIActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

}