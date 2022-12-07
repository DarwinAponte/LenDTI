package com.example.lendti;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.lendti.Client.ReservaDispositivosActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.FieldIndex;

import java.io.IOException;

public class BlankFragmentReserva extends Fragment {

    private static final int GALERYDNI_INTENT=3;
    private static final int REQUEST_IMAGE_CAPTUREDNI = 4;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    ImageButton btnGaleria2;
    ImageButton BtnFoto2;
    ImageView imageViewfotoDNI;
    ProgressDialog mprogressDialog;
    String idquipo;

    View view;


    public BlankFragmentReserva() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        view=inflater.inflate(R.layout.fragment_blank_reserva, container, false);

        Bundle bundle = getArguments();
        idquipo=bundle.getString("IDEQUIPO");



        imageViewfotoDNI=view.findViewById(R.id.imageViewFotodni);

        btnGaleria2= (ImageButton) view.findViewById(R.id.GaleriaReserva2);
        BtnFoto2=(ImageButton) view.findViewById(R.id.imageButtonCameraReserva);

        BtnFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentcamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentcamera, REQUEST_IMAGE_CAPTUREDNI);
            }
        });


        btnGaleria2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALERYDNI_INTENT);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==GALERYDNI_INTENT && resultCode==RESULT_OK) {

            Uri uri = data.getData();
            Intent intent = new Intent(getActivity(), ReservaDispositivosActivity.class);
            intent.putExtra("idEquipo", idquipo);
            intent.setData(uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }


        if(requestCode==REQUEST_IMAGE_CAPTUREDNI && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBit = (Bitmap) extras.get("data");
            Intent intentbip = new Intent(getActivity(),ReservaDispositivosActivity.class);
            intentbip.putExtra("idEquipo",idquipo);
            intentbip.putExtra("lalala",imageBit);
            intentbip.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentbip);


        }
    }
}