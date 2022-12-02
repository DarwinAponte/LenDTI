package com.example.lendti.UserIT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lendti.BottomSheetMenuFragment;
import com.example.lendti.Entity.UserIT;
import com.example.lendti.R;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.OnClick;

public class PerfilActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    TextView nombre,codigo,correo;
    ImageView imagePerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        nombre = findViewById(R.id.tvPerfilNombre);
        codigo = findViewById(R.id.tvPerfilCodigo);
        correo = findViewById(R.id.tvPerfilCorreo);
        imagePerfil = findViewById(R.id.imagViewPerfil);

        firebaseFirestore.collection("users").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserIT userIT = documentSnapshot.toObject(UserIT.class);
                nombre.setText(userIT.getNombre());
                codigo.setText(userIT.getCodigo());
                correo.setText(userIT.getCorreo());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        setBottomNavigationView();

    }

    @OnClick(R.id.ibPerfilCambiarfoto)
    public void showBottomSheetGrid(View view) {
        BottomSheetMenuFragment frg = BottomSheetMenuFragment.createInstanceGrid();
        frg.show(getSupportFragmentManager(), BottomSheetMenuFragment.class.getSimpleName());


    }


    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationPerfilUserIT);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.page_perfil);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_inicio:
                        Intent i  = new Intent(PerfilActivity.this,ListaEquipoActivity.class);
                        i.putExtra("main","main");
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_gestion:
                        Intent i1  = new Intent(PerfilActivity.this,ListaEquipoActivity.class);
                        i1.putExtra("lista","lista");
                        startActivity(new Intent(i1));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_solicitudes:
                        startActivity(new Intent(PerfilActivity.this,SolicitudActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_perfil:
                        return true;
                }
                return false;
            }
        });
    }
}