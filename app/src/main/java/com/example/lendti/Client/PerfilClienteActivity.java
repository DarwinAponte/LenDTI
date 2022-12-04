package com.example.lendti.Client;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lendti.BlankFragment;
import com.example.lendti.BottomSheetMenuFragmentPerfil;
import com.example.lendti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.OnClick;

public class PerfilClienteActivity extends AppCompatActivity {

    FragmentTransaction transaction;
    Fragment fragmentPerfil, fragmentInicio;

    ImageButton foto;

    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    StorageReference miStorage;
    private TextView clientNombre,clientCodigo,clientRol,clientCorreo;
    private String IDcliente;
    ImageView imageViewla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);

        fragmentPerfil = new BottomSheetMenuFragmentPerfil();
        fragmentInicio = new BlankFragment();
        miStorage= FirebaseStorage.getInstance().getReference();

        clientNombre=findViewById(R.id.textView48);
        clientCodigo=findViewById(R.id.textView50);
        clientRol=findViewById(R.id.textView52);
        clientCorreo=findViewById(R.id.textView54);
        imageViewla=findViewById(R.id.imageView8);
        foto=findViewById(R.id.imageButtonPerfil);
        mAuth=FirebaseAuth.getInstance();

        firestore=FirebaseFirestore.getInstance();
        IDcliente=mAuth.getCurrentUser().getUid();

        getSupportFragmentManager().beginTransaction().add(R.id.FramePerfil,fragmentInicio).commit();



        DocumentReference docRef = firestore.collection("clientes").document(IDcliente);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        clientNombre.setText(document.getString("nombre"));
                        clientCodigo.setText(document.getString("codigo"));
                        clientRol.setText(document.getString("rol"));
                        clientCorreo.setText(document.getString("correo"));
                        String fotoPerfil=document.getString("urlFoto");
                        try {
                            if(!fotoPerfil.equals("")){
                                Toast.makeText(PerfilClienteActivity.this,"Cargando foto",Toast.LENGTH_SHORT).show();
                                Glide.with(PerfilClienteActivity.this)
                                        .load(fotoPerfil)
                                        .into(imageViewla);

                            }
                        }catch (Exception e){
                            System.out.println("Holaaa Stephania");
                        }

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }


    public void yaBasta(View view){
        transaction=getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.imageButtonPerfil:transaction.replace(R.id.FramePerfil,fragmentPerfil);
                break;
            case R.id.imageButton4:transaction.replace(R.id.FramePerfil,fragmentInicio);
                break;
        }
        transaction.commit();
    }


}