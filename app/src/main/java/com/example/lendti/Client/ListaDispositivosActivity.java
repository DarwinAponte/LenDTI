package com.example.lendti.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import androidx.appcompat.widget.SearchView;

import com.example.lendti.Adapter.DispositivosAdapter;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class ListaDispositivosActivity extends AppCompatActivity {

    RecyclerView mRecycler;
    DispositivosAdapter mdispositivosAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_dispositivos);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mRecycler = findViewById(R.id.recyclerViewdispositivos);
        searchView=findViewById(R.id.searchView);


        mRecycler.setLayoutManager(new GridLayoutManager(this,2));
        Query query = firestore.collection("equipos");
        FirestoreRecyclerOptions<Equipo> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query,Equipo.class).build();
        mdispositivosAdapter = new DispositivosAdapter(firestoreRecyclerOptions,ListaDispositivosActivity.this);
        mdispositivosAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mdispositivosAdapter);

        search_View();


    }


    @Override
    protected void onStart() {
        super.onStart();
        mRecycler.getRecycledViewPool().clear();
        mdispositivosAdapter.notifyDataSetChanged();
        mdispositivosAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mdispositivosAdapter.stopListening();
    }

    public void vista1 (View view){

        Intent intent = new Intent(this, SolicitudDevicesActivity.class);
        startActivity(intent);
    }
    private void search_View(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSearch(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                textSearch(s);
                return false;
            }
        });
    }
    public void textSearch(String s){
        Query query = firestore.collection("equipos");
        FirestoreRecyclerOptions<Equipo> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query.orderBy("tipo").startAt(s)
                        .endAt(s+"~"),Equipo.class).build();
        mdispositivosAdapter = new DispositivosAdapter(firestoreRecyclerOptions,ListaDispositivosActivity.this);
        mdispositivosAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mdispositivosAdapter);

    }
}