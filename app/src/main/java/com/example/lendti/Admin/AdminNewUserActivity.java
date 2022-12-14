package com.example.lendti.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lendti.Entity.UserTI;
import com.example.lendti.R;
import com.example.lendti.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminNewUserActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    List<String> listaDocuments = new ArrayList<>();
    boolean exist = false;
    Button crearUserTI;
    EditText editTextNombre,editTextCorreo,editTextCodigo,editTextPass;

    //Para la foto de perfil
    private String fotoPerfilUrl="https://firebasestorage.googleapis.com/v0/b/lendti-3e9e3.appspot.com/o/Avatars%2FCliente.png?alt=media&token=2c8c7037-e4ce-4d48-b9b3-7f49bad85336";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_user);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        editTextNombre=(EditText) findViewById(R.id.text_admNewUserNombre);
        editTextCorreo=(EditText) findViewById(R.id.text_admNewUserCorreo);
        editTextCodigo=(EditText) findViewById(R.id.text_admNewUserC??digo);
        editTextPass=(EditText) findViewById(R.id.text_admNewUserPass);
        crearUserTI=(Button) findViewById(R.id.btn_admNewUserCrear);
        crearUserTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearNuevoUsuarioTI();
            }
        });
        firebaseFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }
                for (QueryDocumentSnapshot document : value){
                    listaDocuments.add(document.getId());
                }
            }
        });



    }

    public void crearNuevoUsuarioTI(){
        String nombre=editTextNombre.getText().toString();
        String correo=editTextCorreo.getText().toString();
        String codigo=editTextCodigo.getText().toString();
        String passwd=editTextPass.getText().toString();
        if(listaDocuments.contains(correo)){
            exist = true;
        }
        if(exist){
            Toast.makeText(AdminNewUserActivity.this,"Este correo ya tiene una cuenta asociada",Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.createUserWithEmailAndPassword(correo,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String UserId = firebaseAuth.getCurrentUser().getUid();
                    UserTI userITNew= new UserTI(nombre,correo,codigo,passwd,fotoPerfilUrl);
                    firebaseFirestore.collection("users").document(UserId).set(userITNew).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            firebaseDatabase.getInstance().getReference("UsuariosTI")
                                            .child(UserId).setValue(userITNew)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(AdminNewUserActivity.this,"Se ha registrado correctamente",Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(AdminNewUserActivity.this,AdminActivity.class));
                                                        finish();

                                                    }
                                                }
                                            });





                        }
                    });
                }
            });
        }

    }
}