package com.example.lendti.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SolicitudDevicesActivity extends AppCompatActivity {
    EditText tipo;
    EditText marca;
    EditText caracteristicas;
    EditText incluye;
    Button btnSolicitar;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_devices);


        String id =  getIntent().getStringExtra("idEquipo");

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        tipo = findViewById(R.id.editTextTextPersonName5);
        marca = findViewById(R.id.editTextTextPersonName6);
        caracteristicas = findViewById(R.id.editTextTextPersonName7);
        incluye = findViewById(R.id.editTextTextPersonName8);
        btnSolicitar=findViewById(R.id.buttonSolici);

        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SolicitudDevicesActivity.this,ReservaDispositivosActivity.class);
                intent.putExtra("idEquipo",id);
                System.out.println(id);
                System.out.println("HOLAAAA JOSEE");
                startActivity(intent);
            }
        });



        tipo.setFocusable(false);
        tipo.setTextIsSelectable(false);
        marca.setFocusable(false);
        marca.setTextIsSelectable(false);
        caracteristicas.setFocusable(false);
        caracteristicas.setTextIsSelectable(false);
        incluye.setFocusable(false);
        incluye.setTextIsSelectable(false);
        obtenerEquipo(id);
    }

    public void obtenerEquipo(String id){
        firebaseFirestore.collection("equipos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Equipo equipo = documentSnapshot.toObject(Equipo.class);
                tipo.setText(equipo.getTipo());
                marca.setText(equipo.getMarca());
                caracteristicas.setText(equipo.getCaracteristicas());
                incluye.setText(equipo.getIncluye());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SolicitudDevicesActivity.this,"Error al obtener los datos",Toast.LENGTH_SHORT).show();
            }
        });
    }

}