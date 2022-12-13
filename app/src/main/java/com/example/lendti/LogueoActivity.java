package com.example.lendti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearSnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lendti.Admin.AdminActivity;
import com.example.lendti.Client.ListaClienteActivity;
import com.example.lendti.UserIT.ListaEquipoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LogueoActivity extends AppCompatActivity {

    Button btnloguear;
    EditText emailLogueo;
    EditText passwordLogueo;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String rolLogueo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueo);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        btnloguear = findViewById(R.id.buttonAcceder);
        emailLogueo = findViewById(R.id.editTextCorreoLogueo);
        passwordLogueo = findViewById(R.id.editTextPasswordLogueo);




        TextView registrar = findViewById(R.id.clickRegister);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogueoActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnloguear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =  emailLogueo.getText().toString().trim();
                String password = passwordLogueo.getText().toString();

                if(email.isEmpty() || password.isEmpty() ){
                    Toast.makeText(LogueoActivity.this,"El correo o la contraseña no pueden ser vacias",Toast.LENGTH_SHORT).show();
                }else{
                    loginUsuario(email,password);
                }

            }
        });




    }

    public void loginUsuario(String email,String password){

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                System.out.println(email);
                System.out.println(password);
                if(task.isSuccessful()){
                    firebaseFirestore.collection("clientes").whereEqualTo("correo",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                System.out.println(task.getResult().isEmpty());
                                if(task.getResult().isEmpty()){
                                    firebaseFirestore.collection("users").whereEqualTo("correo",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                System.out.println(task.getResult().isEmpty());
                                                if(task.getResult().isEmpty()){
                                                    firebaseFirestore.collection("admins").whereEqualTo("correo",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                System.out.println(task.getResult().isEmpty());
                                                                if(task.getResult().isEmpty()){
                                                                    Toast.makeText(LogueoActivity.this,"No esta asociado este correo",Toast.LENGTH_SHORT).show();
                                                                }else {
                                                                    startActivity(new Intent(LogueoActivity.this, AdminActivity.class));
                                                                    Toast.makeText(LogueoActivity.this,"Bienvenido Administrador",Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    });
                                                }else{
                                                    Intent i = new Intent(LogueoActivity.this, ListaEquipoActivity.class);
                                                    i.putExtra("main","main");
                                                    i.putExtra("lista","");
                                                    startActivity(i);
                                                    finish();
                                                    Toast.makeText(LogueoActivity.this,"Bienvenido UsuarioTI",Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        }
                                    });
                                }else{
                                    startActivity(new Intent(LogueoActivity.this, ListaClienteActivity.class));
                                    finish();
                                    Toast.makeText(LogueoActivity.this,"Bienvenido Cliente",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(LogueoActivity.this,"Error en las credenciales",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}