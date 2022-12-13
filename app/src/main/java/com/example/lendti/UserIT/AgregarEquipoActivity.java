package com.example.lendti.UserIT;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.lendti.Adapter.EquipoITAdapter;
import com.example.lendti.Adapter.PhotoAdapter;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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
    ImageButton btngaleria,btncamara;
    ProgressBar pbfoto;
    GridLayout glEquipos;
    ImageSlider isequipo;
    private Uri cameraUri;
    Equipo equipo;

    String id;
    String ver;
    private List<String> listFotos = new ArrayList<>();
    PhotoAdapter fotoAdapter;
    Timestamp timestap;
    ActivityResultLauncher<Intent> launcherPhotoDocument = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    compressImageAndUpload(uri,50);
                } else {
                    Toast.makeText(AgregarEquipoActivity.this, "Debe seleccionar un archivo", Toast.LENGTH_SHORT).show();
                }
            }
    );
    ActivityResultLauncher<Intent> launcherPhotoCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    compressImageAndUpload(cameraUri,25);
                } else {
                    Toast.makeText(AgregarEquipoActivity.this, "Debe seleccionar un archivo", Toast.LENGTH_SHORT).show();
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipo);

        id =  getIntent().getStringExtra("idEquipo");
        ver = getIntent().getStringExtra("ver");
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference().child("images/");




        buttonEditar = findViewById(R.id.buttonEditar);
        buttonEliminar = findViewById(R.id.buttonEliminar);
        btnagregar = findViewById(R.id.buttonAgregar);
        tipo = findViewById(R.id.editTextTipo);
        marca = findViewById(R.id.editTextMarca);
        caracteristicas = findViewById(R.id.editTextCaracteristicas);
        incluye = findViewById(R.id.editTextTextIncluye);
        stock =  findViewById(R.id.editTextNumberStock);
        btngaleria = findViewById(R.id.imageBtnGaleria);
        rvImagen = findViewById(R.id.recycleviewImagenEquipo);
        btncamara = findViewById(R.id.imageBtnCamara);
        pbfoto = findViewById(R.id.pbAddPhotoEquipo);
        glEquipos= findViewById(R.id.glCreateEquipo);
        isequipo = findViewById(R.id.isEquiposImages);



        if(id==null || id ==""){

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            fotoAdapter = new PhotoAdapter(this, listFotos);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 6);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (position % 5) {
                        case 0:
                        case 1:
                        case 2:
                            return 2;
                        case 3:
                        case 4:
                            return 3;
                    }
                    throw new IllegalStateException("internal error");
                }
            });

            rvImagen.setAdapter(fotoAdapter);
            rvImagen.setLayoutManager(gridLayoutManager);
            evaluarEmpty();
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
                        agregarEquipo(tipoEquipo,marcaEquipo,crtEquipo,incluyeEquipo,stockEquipo,listFotos);
                    }
                }
            });
        }else{
            if(ver==null || ver.equals("")){
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
                            actualizarEquipo(tipoEquipo,marcaEquipo,crtEquipo,incluyeEquipo,stockEquipo,id,listFotos);
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
                btncamara.setVisibility(View.GONE);
                btngaleria.setVisibility(View.GONE);
                isequipo.setVisibility(View.VISIBLE);
                glEquipos.setVisibility(View.GONE);
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
                        btncamara.setVisibility(View.VISIBLE);
                        btngaleria.setVisibility(View.VISIBLE);
                        isequipo.setVisibility(View.GONE);
                        glEquipos.setVisibility(View.VISIBLE);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(AgregarEquipoActivity.this, 2);
                        fotoAdapter = new PhotoAdapter(AgregarEquipoActivity.this, listFotos);
                        GridLayoutManager layoutManager = new GridLayoutManager(AgregarEquipoActivity.this, 6);
                        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                switch (position % 5) {
                                    case 0:
                                    case 1:
                                    case 2:
                                        return 2;
                                    case 3:
                                    case 4:
                                        return 3;
                                }
                                throw new IllegalStateException("internal error");
                            }
                        });

                        rvImagen.setAdapter(fotoAdapter);
                        rvImagen.setLayoutManager(gridLayoutManager);
                        evaluarEmpty();
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
                                    actualizarEquipo(tipoEquipo, marcaEquipo, crtEquipo, incluyeEquipo, stockEquipo, id,listFotos);
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

    public void agregarEquipo(String tipo,String marca,String caract, String incluye,String stock,List<String> lista){

        if(lista.size()!=3){
            Toast.makeText(AgregarEquipoActivity.this,"Porfavor agregue 3 fotos",Toast.LENGTH_SHORT).show();
        }else{
            Equipo equipo = new Equipo(tipo,marca,caract,incluye,stock,lista);
            equipo.setTimestamp(Timestamp.now());
            firebaseFirestore.collection("equipos").add(equipo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(AgregarEquipoActivity.this,"Agregado exitosamente",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AgregarEquipoActivity.this,ListaEquipoActivity.class);
                    i.putExtra("lista","lista");
                    startActivity(i);
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


    public void actualizarEquipo(String tipo,String marca,String caract, String incluye,String stock,String id,List<String> lista){

        if(lista.size() != 3){
            Toast.makeText(AgregarEquipoActivity.this,"Porfavor agregue 3 fotos",Toast.LENGTH_SHORT).show();
        }else {
            Equipo equipo = new Equipo(tipo,marca,caract,incluye,stock,lista);
            equipo.setTimestamp(Timestamp.now());
            firebaseFirestore.collection("equipos").document(id).set(equipo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AgregarEquipoActivity.this, "Actualizado exitosamente", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AgregarEquipoActivity.this, ListaEquipoActivity.class);
                    i.putExtra("lista", "lista");
                    startActivity(i);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AgregarEquipoActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void obtenerEquipo(String id){
        firebaseFirestore.collection("equipos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                equipo = documentSnapshot.toObject(Equipo.class);
                tipo.setText(equipo.getTipo());
                marca.setText(equipo.getMarca());
                caracteristicas.setText(equipo.getCaracteristicas());
                incluye.setText(equipo.getIncluye());
                stock.setText(equipo.getStock());
                listFotos = equipo.getListaFotos();
                if(ver==null){
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(AgregarEquipoActivity.this, 2);
                    fotoAdapter = new PhotoAdapter(AgregarEquipoActivity.this, listFotos);
                    GridLayoutManager layoutManager = new GridLayoutManager(AgregarEquipoActivity.this, 6);
                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            switch (position % 5) {
                                case 0:
                                case 1:
                                case 2:
                                    return 2;
                                case 3:
                                case 4:
                                    return 3;
                            }
                            throw new IllegalStateException("internal error");
                        }
                    });

                    rvImagen.setAdapter(fotoAdapter);
                    rvImagen.setLayoutManager(gridLayoutManager);
                    evaluarEmpty();
                }else{
                    ArrayList<SlideModel> slideModels = new ArrayList<>();
                    for (String url : equipo.getListaFotos()){
                        slideModels.add(new SlideModel(url, ScaleTypes.CENTER_CROP));
                    }
                    isequipo.setImageList(slideModels);
                }
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


    public void compressImageAndUpload(Uri uri, int quality){
        try{
            Bitmap originalImage = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            originalImage.compress(Bitmap.CompressFormat.JPEG,quality,stream);
            subirImagenAFirebase(stream.toByteArray());
        }catch (Exception e){
            Log.d("msg","error",e);
        }
    }

    public void subirImagenAFirebase(byte[] imageBytes) {
        StorageReference photoChild = FirebaseStorage.getInstance().getReference().child("images/equipos/" + "photo_" + Timestamp.now().getSeconds() + ".jpg");
        pbfoto.setVisibility(View.VISIBLE);
        photoChild.putBytes(imageBytes).addOnSuccessListener(taskSnapshot -> {
            pbfoto.setVisibility(View.GONE);
            photoChild.getDownloadUrl().addOnSuccessListener(uri -> {
                listFotos.add(uri.toString());
                fotoAdapter.notifyDataSetChanged();
                evaluarEmpty();
            }).addOnFailureListener(e ->{
                Toast.makeText(AgregarEquipoActivity.this, "Hubo un error al subir la imagen", Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(e -> {
            pbfoto.setVisibility(View.GONE);
            Toast.makeText(AgregarEquipoActivity.this, "Hubo un error al subir la imagen", Toast.LENGTH_SHORT).show();
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                long bytesTransferred = snapshot.getBytesTransferred();
                long totalByteCount = snapshot.getTotalByteCount();
                double progreso = (100.0 * bytesTransferred) / totalByteCount;
                Long round = Math.round(progreso);
                pbfoto.setProgress(round.intValue());
            }
        });
    }

    public void evaluarEmpty(){
        if (listFotos.size()>0){
            rvImagen.setVisibility(View.VISIBLE);
            glEquipos.setVisibility(View.GONE);
        }else{
            rvImagen.setVisibility(View.GONE);
            glEquipos.setVisibility(View.VISIBLE);
        }
    }

    public void removerFoto(int position){
        listFotos.remove(position);
        fotoAdapter.notifyDataSetChanged();
        evaluarEmpty();
    }

    public void uploadPhotoFromDocument(View view) {
        if (pbfoto.getVisibility()==View.VISIBLE){
            Toast.makeText(AgregarEquipoActivity.this, "Espera a que se termine de subir la foto", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            launcherPhotoDocument.launch(intent);
        }
    }

    public void uploadPhotoFromCamera(View view) {
        if (pbfoto.getVisibility()==View.VISIBLE){
            Toast.makeText(AgregarEquipoActivity.this, "Espera a que se termine de subir la foto", Toast.LENGTH_SHORT).show();
        }else{
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
            cameraUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
            launcherPhotoCamera.launch(cameraIntent);
        }
    }

    public void backButtonUsuariosList(View view){
        Intent i = new Intent(AgregarEquipoActivity.this,ListaEquipoActivity.class);
        i.putExtra("lista","lista");
        startActivity(i);
        finish();

    }






































}