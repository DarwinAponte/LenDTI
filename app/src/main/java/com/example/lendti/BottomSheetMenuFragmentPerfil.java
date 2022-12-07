package com.example.lendti;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lendti.Client.ListaClienteActivity;
import com.example.lendti.Client.PerfilClienteActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;


public class BottomSheetMenuFragmentPerfil extends Fragment {
    private static final int GALERY_INTENT=1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    FirebaseFirestore firestore;
    String storagePath = "images/";
    String storagePath1 = "perfiles/";
    String photo = "photo";
    FirebaseAuth mAuth;
    StorageReference miStorage;
    View view;
    Context context;
    ImageButton btnGaleria;
    ImageButton BtnFoto;
    ImageView imageView;
    ProgressDialog mprogressDialog;



    public BottomSheetMenuFragmentPerfil() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        miStorage= FirebaseStorage.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_grid_bottom_sheet2, container, false);
        btnGaleria=(ImageButton) view.findViewById(R.id.imageButtonGaleria2);

        mprogressDialog=new ProgressDialog(getActivity());

        imageView=view.findViewById(R.id.imageView8);

        BtnFoto=(ImageButton) view.findViewById(R.id.imageButtonCamera2);

        BtnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, REQUEST_IMAGE_CAPTURE);

            }
        });



        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALERY_INTENT);

            }
        });
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALERY_INTENT && resultCode==RESULT_OK){

            mprogressDialog.setTitle("Subiendo Foto....");
            mprogressDialog.setMessage("subiendo foto a firebase");
            mprogressDialog.setCancelable(false);
            mprogressDialog.show();


            Uri uri= data.getData();
            String subirStorage= storagePath+ "" +storagePath1 +""+photo + "" + mAuth.getCurrentUser().getUid()+"";
            StorageReference filePath = miStorage.child(subirStorage).child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());
                        if (uriTask.isSuccessful()){
                            uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(getActivity(),"Se subio exitosamente",Toast.LENGTH_SHORT).show();
                                    mprogressDialog.dismiss();
                                    String download_uri=uri.toString();
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("urlFoto",download_uri);
                                    firestore.collection("clientes").document(mAuth.getCurrentUser().getUid()).update(map);
                                    Intent intent=new Intent(getActivity(), PerfilClienteActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                        }
                }
            });
        }
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            mprogressDialog.setTitle("Subiendo Foto desde Camara....");
            mprogressDialog.setMessage("subiendo foto a firebase");
            mprogressDialog.setCancelable(false);
            mprogressDialog.show();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String subirStorage= storagePath+ "" +storagePath1 +""+photo + "" + mAuth.getCurrentUser().getUid()+"";
            String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());

            StorageReference filePath = miStorage.child(subirStorage).child(timeStamp);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] datas= baos.toByteArray();

            UploadTask uploadTask=filePath.putBytes(datas);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());
                    if (uriTask.isSuccessful()){
                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(getActivity(),"Se subio exitosamente",Toast.LENGTH_SHORT).show();
                                mprogressDialog.dismiss();
                                String download_uri=uri.toString();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("urlFoto",download_uri);
                                firestore.collection("clientes").document(mAuth.getCurrentUser().getUid()).update(map);
                                Intent intent=new Intent(getActivity(), PerfilClienteActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"Hubo un error al subir por camara",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}