package com.example.lendti.Client;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lendti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class PerfilClienteActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    private TextView clientNombre,clientCodigo,clientRol,clientCorreo;
    private String IDcliente,clientID,clientEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);
        clientNombre=findViewById(R.id.textView48);
        String jose=clientNombre.getText().toString();
        clientCodigo=findViewById(R.id.textView50);
        clientRol=findViewById(R.id.textView52);
        clientCorreo=findViewById(R.id.textView54);
        mAuth=FirebaseAuth.getInstance();

        firestore=FirebaseFirestore.getInstance();
        IDcliente=mAuth.getCurrentUser().getUid();
        System.out.println(mAuth.getCurrentUser().getUid());
        System.out.println(mAuth.getCurrentUser().getEmail());

        DocumentReference docRef = firestore.collection("clientes").document(IDcliente);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        clientNombre.setText(document.getString("nombre"));
                        clientCodigo.setText(document.getString("dni"));
                        clientRol.setText(document.getString("rol"));
                        clientCorreo.setText(document.getString("correo"));

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


    }
    public void otraVista(View view){
        Intent intent = new Intent(this,ListaClienteActivity.class);
        startActivity(intent);


    }


}