package com.example.lendti.Client;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lendti.Adapter.ListadeSolicitudesAprobadasAdapter;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.Entity.Solicitud;
import com.example.lendti.R;
import com.example.lendti.UserIT.AgregarEquipoActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ListaClienteActivity extends AppCompatActivity {
    RecyclerView mRecycler;
    ListadeSolicitudesAprobadasAdapter mListaAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_lista);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        mRecycler = findViewById(R.id.recyclerViewAprobadas);
        mRecycler.setLayoutManager(new LinearLayoutManager(ListaClienteActivity.this));
        Query query = firestore.collection("solicitudes").whereEqualTo("uidCliente",uid).whereEqualTo("estado","aprobada");
        FirestoreRecyclerOptions<Solicitud> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Solicitud>().setQuery(query, Solicitud.class).build();
        mListaAdapter = new ListadeSolicitudesAprobadasAdapter(firestoreRecyclerOptions);
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

    public void pendiente(View view){
        Intent intent = new Intent(this,ListaSolicitudesActivity.class);
        startActivity(intent);
    }



}