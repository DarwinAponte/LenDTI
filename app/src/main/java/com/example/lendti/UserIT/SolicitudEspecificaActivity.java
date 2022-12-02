package com.example.lendti.UserIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lendti.Dialogos.SolicitudRechazoFragment;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SolicitudEspecificaActivity extends AppCompatActivity {

    TextView nombre,motivo,curso,programas,prestamo,otros,tipo,marca,caracteristicas,incluye;
    FirebaseFirestore firebaseFirestore;
    Button btnAceptar,btnRechazar;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_especifica);

        firebaseFirestore = FirebaseFirestore.getInstance();

        String id =  getIntent().getStringExtra("idSolicitud");
        nombre = findViewById(R.id.tvClienteSoliUserIT);
        motivo = findViewById(R.id.tvMotivoSoliUserIT);
        curso = findViewById(R.id.tvCursoSoliUserIT);
        programas =  findViewById(R.id.tvProgramasSoliUserIT);
        prestamo = findViewById(R.id.tvTiempoPrestamoSoliUserIT);
        otros = findViewById(R.id.tvOtrosSoliUserIT);
        tipo = findViewById(R.id.tvTipoSoliUserIT);
        marca = findViewById(R.id.tvMarcaSoliUserIT);
        caracteristicas = findViewById(R.id.tvCaracteristicasSoliUserIT);
        incluye = findViewById(R.id.tvIncluyeSoliUserIT);

        obtenerSolicitud(id);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarAlertSoliAceptar();
            }
        });

        btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SolicitudRechazoFragment solicitudRechazoFragment = new SolicitudRechazoFragment();
                solicitudRechazoFragment.show(getSupportFragmentManager(),"DialogoRechazo");
            }
        });
        setBottomNavigationView();
    }


    public void obtenerSolicitud(String id){
        firebaseFirestore.collection("solicitudes").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombreSoli = documentSnapshot.getString("nombre");
                String motivoSoli = documentSnapshot.getString("motivo");
                String cursoSoli = documentSnapshot.getString("curso");
                String programasSoli = documentSnapshot.getString("programas");
                String prestamoSoli = documentSnapshot.getString("prestamo");
                String otrosSoli = documentSnapshot.getString("otros");
                String tipoSoli = documentSnapshot.getString("tipo");
                String marcaSoli = documentSnapshot.getString("marca");
                String caracSoli = documentSnapshot.getString("caracteristicas");
                String incluyeSoli = documentSnapshot.getString("incluye");

                nombre.setText(nombreSoli);
                motivo.setText(motivoSoli);
                curso.setText(cursoSoli);
                programas.setText(programasSoli);
                prestamo.setText(prestamoSoli);
                otros.setText(otrosSoli);
                tipo.setText(tipoSoli);
                marca.setText(marcaSoli);
                caracteristicas.setText(caracSoli);
                incluye.setText(incluyeSoli);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SolicitudEspecificaActivity.this,"Error al obtener los datos",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void mostrarAlertSoliAceptar(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro de aceptar esta solicitud?");
        alertDialog.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Query query = firebaseFirestore.collection("equipos");

                        FirestoreRecyclerOptions<Equipo> firestoreRecyclerOptions =
                                new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query,Equipo.class).build();


                        startActivity(new Intent(SolicitudEspecificaActivity.this,SolicitudActivity.class));
                    }
                });
        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        alertDialog.show();
    }

    public void mostrarAlertSoliRechazar(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Escriba porque rechazo esta solicitud");
        alertDialog.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Query query = firebaseFirestore.collection("equipos");

                        FirestoreRecyclerOptions<Equipo> firestoreRecyclerOptions =
                                new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query,Equipo.class).build();


                        startActivity(new Intent(SolicitudEspecificaActivity.this,SolicitudActivity.class));
                    }
                });
        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        alertDialog.show();
    }


    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationSolicitudEspecificaUserIT);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.page_gestion);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_inicio:
                        Intent i  = new Intent(SolicitudEspecificaActivity.this,ListaEquipoActivity.class);
                        i.putExtra("main","main");
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_gestion:
                        Intent i1  = new Intent(SolicitudEspecificaActivity.this,ListaEquipoActivity.class);
                        i1.putExtra("lista","lista");
                        startActivity(new Intent(i1));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_solicitudes:
                        startActivity(new Intent(SolicitudEspecificaActivity.this,SolicitudActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_perfil:
                        startActivity(new Intent(SolicitudEspecificaActivity.this,PerfilActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
}