package com.example.lendti.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReservaDispositivosActivity extends AppCompatActivity {

    //EditText tipo;
    //EditText marca;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    Button reservar;
    EditText motivo,curso,programas;
    String tipo,marca;
    String uidUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_dispositivos);

        String id =  getIntent().getStringExtra("idEquipo");
        System.out.println(id);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uidUsuario=mAuth.getCurrentUser().getUid();

        motivo=findViewById(R.id.motivo);
        curso=findViewById((R.id.curso));
        programas=findViewById(R.id.programsNece);
        reservar=findViewById(R.id.buttonReservar);


        obtenerEquipo(id);

        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String motivo2=motivo.getText().toString().trim();
                String curs2=curso.getText().toString().trim();
                String programas2 = programas.getText().toString().trim();
                String tipo2 =tipo.trim();
                String marca2=marca.trim();


                if(motivo2.isEmpty() && curs2.isEmpty() && programas2.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingresar los datos", Toast.LENGTH_SHORT).show();
                }else{
                    putSolicitud(motivo2,curs2,programas2,tipo2,marca2);

                }
            }
        });


    }

    private void putSolicitud(String motivo2, String curs2, String programas2, String tipo2,String marca2) {
        Map<String,Object> map= new HashMap<>();
        map.put("motivo", motivo2);
        map.put("curso", curs2);
        map.put("programas", programas2);
        map.put("tipo", tipo2);
        map.put("marca", marca2);
        map.put("estado","pendiente");
        map.put("uid",uidUsuario);
        firebaseFirestore.collection("solicitudes").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Creado de manera exitosa", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"error al ingresar", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void obtenerEquipo(String id){
        firebaseFirestore.collection("equipos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Equipo equipo = documentSnapshot.toObject(Equipo.class);
                tipo = equipo.getTipo();
                marca = equipo.getMarca();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReservaDispositivosActivity.this,"Error al obtener los datos",Toast.LENGTH_SHORT).show();
            }
        });
    }
}