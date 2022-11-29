package com.example.lendti.UserIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lendti.BottomSheetMenuFragment;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.OnClick;

public class AgregarEquipoActivity extends AppCompatActivity {

    Button btnagregar;
    EditText tipo;
    EditText marca;
    EditText caracteristicas;
    EditText incluye;
    EditText stock;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipo);

        String id =  getIntent().getStringExtra("idEquipo");
        firebaseFirestore = FirebaseFirestore.getInstance();



        btnagregar = findViewById(R.id.buttonAgregar);
        tipo = findViewById(R.id.editTextTipo);
        marca = findViewById(R.id.editTextMarca);
        caracteristicas = findViewById(R.id.editTextCaracteristicas);
        incluye = findViewById(R.id.editTextTextIncluye);
        stock =  findViewById(R.id.editTextNumberStock);

        if(id==null || id ==""){
            btnagregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tipoEquipo = tipo.getText().toString().trim();
                    String marcaEquipo = marca.getText().toString().trim();
                    String crtEquipo = caracteristicas.getText().toString().trim();
                    String incluyeEquipo = incluye.getText().toString().trim();
                    String stockEquipo = stock.getText().toString().trim();
                    if(tipoEquipo.isEmpty() || marcaEquipo.isEmpty() || crtEquipo.isEmpty() || incluyeEquipo.isEmpty() | stockEquipo.isEmpty()){
                        Toast.makeText(AgregarEquipoActivity.this,"Los campos no pueden estar vacios",Toast.LENGTH_SHORT).show();
                    }else{
                        agregarEquipo(tipoEquipo,marcaEquipo,crtEquipo,incluyeEquipo,stockEquipo);
                    }
                }
            });
        }else{
            btnagregar.setText("Actualizar");
            obtenerEquipo(id);
            btnagregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tipoEquipo = tipo.getText().toString().trim();
                    String marcaEquipo = marca.getText().toString().trim();
                    String crtEquipo = caracteristicas.getText().toString().trim();
                    String incluyeEquipo = incluye.getText().toString().trim();
                    String stockEquipo = stock.getText().toString().trim();
                    if(tipoEquipo.isEmpty() || marcaEquipo.isEmpty() || crtEquipo.isEmpty() || incluyeEquipo.isEmpty() | stockEquipo.isEmpty()){
                        Toast.makeText(AgregarEquipoActivity.this,"Los campos no pueden estar vacios",Toast.LENGTH_SHORT).show();
                    }else{
                        actualizarEquipo(tipoEquipo,marcaEquipo,crtEquipo,incluyeEquipo,stockEquipo,id);
                    }
                }
            });

        }



    }

    public void agregarEquipo(String tipo,String marca,String caract, String incluye,String stock){
        Equipo equipo = new Equipo();
        equipo.setTipo(tipo);
        equipo.setMarca(marca);
        equipo.setCaracteristicas(caract);
        equipo.setIncluye(incluye);
        equipo.setStock(stock);
        firebaseFirestore.collection("equipos").add(equipo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AgregarEquipoActivity.this,"Agregado exitosamente",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AgregarEquipoActivity.this,ListaEquipoActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AgregarEquipoActivity.this,"Error al agregar",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void actualizarEquipo(String tipo,String marca,String caract, String incluye,String stock,String id){
        Equipo equipo = new Equipo();
        equipo.setTipo(tipo);
        equipo.setMarca(marca);
        equipo.setCaracteristicas(caract);
        equipo.setIncluye(incluye);
        equipo.setStock(stock);
        firebaseFirestore.collection("equipos").document(id).set(equipo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AgregarEquipoActivity.this,"Actualizado exitosamente",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AgregarEquipoActivity.this,ListaEquipoActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AgregarEquipoActivity.this,"Error al actualizar",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void obtenerEquipo(String id){
        firebaseFirestore.collection("equipos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String tipoEquipo = documentSnapshot.getString("tipo");
                String marcaEquipo = documentSnapshot.getString("marca");
                String caracEquipo = documentSnapshot.getString("caracteristicas");
                String incluyeEquipo = documentSnapshot.getString("incluye");
                String stockEquipo = documentSnapshot.getString("stock");
                tipo.setText(tipoEquipo);
                marca.setText(marcaEquipo);
                caracteristicas.setText(caracEquipo);
                incluye.setText(incluyeEquipo);
                stock.setText(stockEquipo);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AgregarEquipoActivity.this,"Error al obtener los datos",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.imageViewInput1)
    public void showBottomSheetGrid(View view) {
        BottomSheetMenuFragment frg = BottomSheetMenuFragment.createInstanceGrid();
        frg.show(getSupportFragmentManager(), BottomSheetMenuFragment.class.getSimpleName());
    }
}