package com.example.lendti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lendti.Entity.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    List<String> listaDocuments = new ArrayList<>();
    boolean exist = false;
    Button btnregistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        btnregistrar = findViewById(R.id.buttonRegister);
        EditText nombre = findViewById(R.id.etNombreRegister);
        EditText apellido = findViewById(R.id.etApellidoRegister);
        EditText codigo = findViewById(R.id.etCodigoRegister);
        Spinner rol = findViewById(R.id.spinnerRol);
        EditText correo = findViewById(R.id.etCorreoRegister);
        EditText password = findViewById(R.id.etContraseniaRegister);
        EditText password1 = findViewById(R.id.etContraseniaRepetidaRegister);


        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rolString = rol.getSelectedItem().toString();
                String nombreString = nombre.getText().toString();
                String apellidoString = apellido.getText().toString();
                String codigoString = codigo.getText().toString();
                String correoString = correo.getText().toString();
                String passwordString = password.getText().toString();
                String password1String = password1.getText().toString();

                if(nombreString.isEmpty() || apellidoString.isEmpty() || codigoString.isEmpty() || correoString.isEmpty() || passwordString.isEmpty() || password1String.isEmpty() || rolString.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Los campos no pueden ser vacios",Toast.LENGTH_SHORT).show();
                }else{
                    registerUsuario(nombreString,apellidoString,codigoString,correoString,passwordString,password1String,rolString);

                }


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

        firebaseFirestore.collection("clientes").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

        firebaseFirestore.collection("admins").addSnapshotListener(new EventListener<QuerySnapshot>() {
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


        TextView logueo = findViewById(R.id.textViewLogueo);
        logueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LogueoActivity.class));
                finish();
            }


        });
    }

    public void registerUsuario(String nombre,String apellido,String codigo,String correo,String password,String password1,String rol){

        if(listaDocuments.contains(correo)){
            exist = true;
        }

        if(exist){
            Toast.makeText(RegisterActivity.this,"Este correo ya tiene una cuenta asociada",Toast.LENGTH_SHORT).show();
        }else if(codigo.length()!=8){
            Toast.makeText(RegisterActivity.this,"El codigo debe contener 8 carácteres",Toast.LENGTH_SHORT).show();
        }else if(rol.equals("rol")){
            Toast.makeText(RegisterActivity.this,"Eliga su rol",Toast.LENGTH_SHORT).show();
        }else if(!password.equals(password1)){
            Toast.makeText(RegisterActivity.this,"Las contraseñas deben ser iguales",Toast.LENGTH_SHORT).show();
        }else{

            firebaseAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    String id = firebaseAuth.getCurrentUser().getUid();
                    Cliente cliente = new Cliente(nombre,apellido,rol,codigo,correo,password,password1);
                    firebaseFirestore.collection("clientes").document(id).set(cliente).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(RegisterActivity.this,LogueoActivity.class));
                            finish();
                            Toast.makeText(RegisterActivity.this,"Se ha registrado correctamente",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this,"Fallo al registrarse",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });




        }


    }




}