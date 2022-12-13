package com.example.lendti.Admin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lendti.Entity.Admin;
import com.example.lendti.LogueoActivity;
import com.example.lendti.R;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
//import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;


public class AdminPerfilFragment extends Fragment {
    View view;

    private Button logOut;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    public DocumentReference docRef;
    private FirebaseUser user;
    private String userID;
    private Admin MiPerfil;
    private ImageView cambiarFoto;
    //Elementos UI
    private CircularImageView fotoPerfil;
    private TextView nombrePerfil,correoPerfil,telefonoPerfil,dniPerfil,horarioPerfil;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_admin_perfil, container, false);


        //Todos lo elementos
        cambiarFoto=view.findViewById(R.id.btnChangeProfile);
        fotoPerfil=(CircularImageView)view.findViewById(R.id.fotoPerfilAdmin);
        nombrePerfil=(TextView)view.findViewById(R.id.nombrePerfilAdmin);
        correoPerfil=(TextView)view.findViewById(R.id.correoPerfilAdmin);
        telefonoPerfil=(TextView)view.findViewById(R.id.telefonoPerfilAdmin);
        dniPerfil=(TextView)view.findViewById(R.id.dniPerfilAdmin);
        horarioPerfil=(TextView)view.findViewById(R.id.horarioPerfilAdmin);


        //Para Cerrar sesion
        logOut=(Button) view.findViewById(R.id.btn_adminPerfilLogOUt);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LogueoActivity.class));
                getActivity().finishAndRemoveTask();
            }
        });

        //Para colocar datos de profile
        user=FirebaseAuth.getInstance().getCurrentUser();
        userID=user.getUid();
        firebaseFirestore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("Admins").child(userID);

        docRef = firebaseFirestore.collection("admins").document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                MiPerfil = documentSnapshot.toObject(Admin.class);
                Log.d("nombre: ",MiPerfil.getNombre());
                CompletarCamposPerfil();
            }
        });

        //PARA CAMBIAR PERFIL
        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},101);
        }
        cambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),101);
            }
        });




        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==101){
            //Setea de manera rápida la imagen
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            fotoPerfil.setImageBitmap(bitmap);
            //para subir la foto s firestorage
            fotoPerfil.setDrawingCacheEnabled(true);
            fotoPerfil.buildDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] databit = baos.toByteArray();

            UploadTask uploadTask = storageReference.child("profile").putBytes(databit);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getContext(), "Ocurrio un error!", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://lendti-3e9e3.appspot.com/Admins/"+userID+"/profile");
                    gsReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String fotoUpdated=uri.toString();
                        docRef.update("urlfoto",fotoUpdated);
                        Toast.makeText(getContext(), "Perfil Actualizado!", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e ->
                            Toast.makeText(view.getContext(), "Error al actualizar! lláma a LEO MORA", Toast.LENGTH_SHORT).show());
                }
            });

        }
    }

    private void CompletarCamposPerfil() {
        nombrePerfil.setText(MiPerfil.getNombre());
        correoPerfil.setText(MiPerfil.getCorreo());
        telefonoPerfil.setText(MiPerfil.getHorario());
        dniPerfil.setText(MiPerfil.getCodigo());
        horarioPerfil.setText(MiPerfil.getDireccion());
        if(!(MiPerfil.getUrlfoto().equals("")||MiPerfil.getUrlfoto()==null)){
            Glide.with(view.getContext()).load(MiPerfil.getUrlfoto()).into(fotoPerfil);
        }

    }

}