package com.example.lendti.Client;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SearchView;

import com.example.lendti.Adapter.DispositivosAdapter;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListaDispositivosActivity extends AppCompatActivity {

    RecyclerView mRecycler;
    DispositivosAdapter mdispositivosAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    SearchView searchView;
    ProgressDialog mprogressDialog;
    Query query;
    Query query2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_dispositivos);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mRecycler = findViewById(R.id.recyclerViewdispositivos);
        mprogressDialog=new ProgressDialog(ListaDispositivosActivity.this);



        mRecycler.setLayoutManager(new GridLayoutManager(this,2));
        query = firestore.collection("equipos");
        FirestoreRecyclerOptions<Equipo> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query,Equipo.class).build();
        mdispositivosAdapter = new DispositivosAdapter(firestoreRecyclerOptions,ListaDispositivosActivity.this);
        mdispositivosAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mdispositivosAdapter);

        searchView=findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String s1 = s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();

                System.out.println(s1);
                CollectionReference documentReference =firestore.collection("equipos");
                query2=documentReference.whereGreaterThanOrEqualTo("tipo",s1);
                AggregateQuery countQuery = query2.count();
                countQuery.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        AggregateQuerySnapshot snapshot = task.getResult();
                        if(snapshot.getCount()!=0){
                            textSearchTipo(s1);
                        }else{
                            textSearchMarca(s1);
                        }

                    } else {
                        Log.d(TAG, "Count failed: ", task.getException());
                    }

                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                CollectionReference documentReference =firestore.collection("equipos");
                //String s1 = s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
                query2=documentReference.whereEqualTo("tipo",s);
                AggregateQuery countQuery = query2.count();
                countQuery.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        AggregateQuerySnapshot snapshot = task.getResult();
                        if(snapshot.getCount()!=0){
                            textSearchTipo(s);
                        }else{
                            textSearchMarca(s);
                        }

                    } else {
                        Log.d(TAG, "Count failed: ", task.getException());
                    }

                });

                return false;
            }
        });


    }


    private void textSearchTipo(String s) {
        FirestoreRecyclerOptions<Equipo> firestoreRecyclerOptions=
                new FirestoreRecyclerOptions.Builder<Equipo>()
                        .setQuery(query.orderBy("tipo").startAt(s).endAt(s+"~"), Equipo.class).build();
        mdispositivosAdapter = new DispositivosAdapter(firestoreRecyclerOptions,ListaDispositivosActivity.this);
        mdispositivosAdapter.notifyDataSetChanged();
        mdispositivosAdapter.startListening();
        mRecycler.setAdapter(mdispositivosAdapter);


    }
    private void textSearchMarca(String s) {
        FirestoreRecyclerOptions<Equipo> firestoreRecyclerOptions=
                new FirestoreRecyclerOptions.Builder<Equipo>()
                        .setQuery(query.orderBy("marca").startAt(s).endAt(s+"~"), Equipo.class).build();
        mdispositivosAdapter = new DispositivosAdapter(firestoreRecyclerOptions,ListaDispositivosActivity.this);
        mdispositivosAdapter.notifyDataSetChanged();
        mdispositivosAdapter.startListening();
        mRecycler.setAdapter(mdispositivosAdapter);


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

}