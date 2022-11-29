package com.example.lendti;

import androidx.annotation.NonNull;
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

import com.example.lendti.UserIT.ListaEquipoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogueoActivity extends AppCompatActivity {

    Button btnloguear;
    EditText emailLogueo;
    EditText passwordLogueo;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueo);

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
                String email =  emailLogueo.getText().toString();
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
                if(task.isSuccessful()){
                    startActivity(new Intent(LogueoActivity.this, ListaEquipoActivity.class));
                    finish();
                    Toast.makeText(LogueoActivity.this,"Bienvenido",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LogueoActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LogueoActivity.this,"Error al iniciar sesión",Toast.LENGTH_SHORT).show();
            }
        });

    }


}