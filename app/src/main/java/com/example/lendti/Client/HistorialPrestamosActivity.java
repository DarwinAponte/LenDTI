package com.example.lendti.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lendti.Adapter.ListaClienteAdapter;
import com.example.lendti.Entity.Solicitud;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HistorialPrestamosActivity extends AppCompatActivity {

    RecyclerView mRecycler;
    ListaClienteAdapter mListaAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_prestamos);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        mRecycler = findViewById(R.id.recyclerViewHistorialPrestamos);
        mRecycler.setLayoutManager(new LinearLayoutManager(HistorialPrestamosActivity.this));
        Query query = firestore.collection("solicitudes").whereEqualTo("uid",uid);
        FirestoreRecyclerOptions<Solicitud> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Solicitud>().setQuery(query, Solicitud.class).build();
        mListaAdapter = new ListaClienteAdapter(firestoreRecyclerOptions);
        mListaAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mListaAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRecycler.getRecycledViewPool().clear();
        mListaAdapter.notifyDataSetChanged();
        mListaAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mListaAdapter.stopListening();
    }
    public void listadispositivos (View view){
        Intent intent = new Intent(this,ListaDispositivosActivity.class);
        startActivity(intent);
    }

}