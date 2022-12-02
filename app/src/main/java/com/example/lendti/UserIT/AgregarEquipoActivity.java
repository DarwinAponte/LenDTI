package com.example.lendti.UserIT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lendti.Adapter.EquipoFotoAdapter;
import com.example.lendti.Adapter.EquipoITAdapter;
import com.example.lendti.BottomSheetMenuFragment;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.OnClick;

public class AgregarEquipoActivity extends AppCompatActivity {

    Button btnagregar;
    Button buttonEditar;
    Button buttonEliminar;
    EditText tipo;
    EditText marca;
    EditText caracteristicas;
    EditText incluye;
    EditText stock;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    StorageReference mStorage;
    EquipoITAdapter equipoITAdapter;
    BottomNavigationView bottomNavigationView;
    RecyclerView rvImagen;
    ImageButton btnGalery,btnCamara;
    ProgressDialog dProgress;
    private static final int GALLERY_INTENT = 1;
    private static final int CODE_SEL_IMAGE = 300;
    Uri imageUrl;
    String idd;
    String storagePath = "equipo/*";
    String photo = "photo";
    List<String> listaPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipo);

        String id =  getIntent().getStringExtra("idEquipo");
        String ver = getIntent().getStringExtra("ver");
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();



        setBottomNavigationView();

        buttonEditar = findViewById(R.id.buttonEditar);
        buttonEliminar = findViewById(R.id.buttonEliminar);
        btnagregar = findViewById(R.id.buttonAgregar);
        tipo = findViewById(R.id.editTextTipo);
        marca = findViewById(R.id.editTextMarca);
        caracteristicas = findViewById(R.id.editTextCaracteristicas);
        incluye = findViewById(R.id.editTextTextIncluye);
        stock =  findViewById(R.id.editTextNumberStock);
        btnGalery = findViewById(R.id.imageBtnGaleria);
        rvImagen = findViewById(R.id.recycleviewImagenEquipo);

        rvImagen.setLayoutManager(new GridLayoutManager(this,2));

        btnGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadPhoto();
            }
        });






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
            if(ver==null || ver==""){
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
            else{
                tipo.setFocusable(false);
                tipo.setTextIsSelectable(false);
                marca.setFocusable(false);
                marca.setTextIsSelectable(false);
                caracteristicas.setFocusable(false);
                caracteristicas.setTextIsSelectable(false);
                incluye.setFocusable(false);
                incluye.setTextIsSelectable(false);
                stock.setFocusable(false);
                stock.setTextIsSelectable(false);
                btnagregar.setVisibility(View.GONE);
                buttonEditar.setVisibility(View.VISIBLE);
                buttonEliminar.setVisibility(View.VISIBLE);
                obtenerEquipo(id);
                buttonEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tipo.setFocusable(true);
                        tipo.setTextIsSelectable(true);
                        marca.setFocusable(true);
                        marca.setTextIsSelectable(true);
                        caracteristicas.setFocusable(true);
                        caracteristicas.setTextIsSelectable(true);
                        incluye.setFocusable(true);
                        incluye.setTextIsSelectable(true);
                        stock.setFocusable(true);
                        stock.setTextIsSelectable(true);
                        buttonEditar.setVisibility(View.GONE);
                        buttonEliminar.setVisibility(View.GONE);
                        btnagregar.setVisibility(View.VISIBLE);
                        btnagregar.setText("Actualizar");
                        btnagregar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String tipoEquipo = tipo.getText().toString().trim();
                                String marcaEquipo = marca.getText().toString().trim();
                                String crtEquipo = caracteristicas.getText().toString().trim();
                                String incluyeEquipo = incluye.getText().toString().trim();
                                String stockEquipo = stock.getText().toString().trim();
                                if (tipoEquipo.isEmpty() || marcaEquipo.isEmpty() || crtEquipo.isEmpty() || incluyeEquipo.isEmpty() | stockEquipo.isEmpty()) {
                                    Toast.makeText(AgregarEquipoActivity.this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                                } else {
                                    actualizarEquipo(tipoEquipo, marcaEquipo, crtEquipo, incluyeEquipo, stockEquipo, id);
                                }
                            }
                        });

                    }
                });

                buttonEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mostrarAlertEquipEliminar(id);
                    }
                });
            }

        }



    }

    public void agregarEquipo(String tipo,String marca,String caract, String incluye,String stock){

        if(listaPhotos.size()<2){
            Toast.makeText(AgregarEquipoActivity.this,"Porfavor agregue Fotos",Toast.LENGTH_SHORT).show();
        }else{
            Equipo equipo = new Equipo();
            equipo.setTipo(tipo);
            equipo.setMarca(marca);
            equipo.setCaracteristicas(caract);
            equipo.setIncluye(incluye);
            equipo.setStock(stock);
            equipo.setListaFotos(listaPhotos);
            firebaseFirestore.collection("equipos").add(equipo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    idd = documentReference.getId();
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
                Equipo equipo = documentSnapshot.toObject(Equipo.class);
                tipo.setText(equipo.getTipo());
                marca.setText(equipo.getMarca());
                caracteristicas.setText(equipo.getCaracteristicas());
                incluye.setText(equipo.getIncluye());
                stock.setText(equipo.getStock());
                listaPhotos = equipo.getListaFotos();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AgregarEquipoActivity.this,"Error al obtener los datos",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void mostrarAlertEquipEliminar(String id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Desea eliminar el equipo de la lista?");
        alertDialog.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Query query = firebaseFirestore.collection("equipos");

                        FirestoreRecyclerOptions<Equipo> firestoreRecyclerOptions =
                                new FirestoreRecyclerOptions.Builder<Equipo>().setQuery(query,Equipo.class).build();

                        equipoITAdapter = new EquipoITAdapter(firestoreRecyclerOptions,AgregarEquipoActivity.this);
                        equipoITAdapter.eliminarEquipo(id);
                        startActivity(new Intent(AgregarEquipoActivity.this,ListaEquipoActivity.class));
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
        bottomNavigationView = findViewById(R.id.bottomNavigationAgregarUserIT);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.page_gestion);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_inicio:
                        Intent i  = new Intent(AgregarEquipoActivity.this,ListaEquipoActivity.class);
                        i.putExtra("main","main");
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_gestion:
                        Intent i1  = new Intent(AgregarEquipoActivity.this,ListaEquipoActivity.class);
                        i1.putExtra("lista","lista");
                        startActivity(new Intent(i1));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_solicitudes:
                        startActivity(new Intent(AgregarEquipoActivity.this,SolicitudActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_perfil:
                        startActivity(new Intent(AgregarEquipoActivity.this,PerfilActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private void uploadPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,CODE_SEL_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == CODE_SEL_IMAGE){
                imageUrl = data.getData();
                subirPhoto(imageUrl);
            }
        }
    }

    private void subirPhoto(Uri imageUrl) {

        dProgress.setMessage("Subiendo Foto");
        dProgress.show();
        String ruteStoragePhoto = storagePath + "" + photo + "" + mAuth.getUid() + "";
        StorageReference reference = mStorage.child(ruteStoragePhoto);
        reference.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                    if(uriTask.isSuccessful()){
                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUri = uri.toString();
                                listaPhotos.add(downloadUri);
                                EquipoFotoAdapter equipoFotoAdapter = new EquipoFotoAdapter(listaPhotos,AgregarEquipoActivity.this);
                                rvImagen.setAdapter(equipoFotoAdapter);
                                rvImagen.setLayoutManager(new GridLayoutManager(AgregarEquipoActivity.this,2));
                            }
                        });
                    }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}