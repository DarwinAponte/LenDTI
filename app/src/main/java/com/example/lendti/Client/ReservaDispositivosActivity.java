package com.example.lendti.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.BitmapImageDecoderResourceDecoder;
import com.example.lendti.BlankFragment;
import com.example.lendti.BlankFragmentReserva;
import com.example.lendti.BottomSheetMenuFragmentPerfil;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservaDispositivosActivity extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    StorageReference miStorage;
    String storagePath = "images/";
    String storagePath1 = "DNI/";
    String photo = "photo/";
    Button reservar,sumar,restar;
    EditText motivo,curso,programas,otros;
    TextView cantidad;
    String tipo,marca,stock;
    String uidUsuario;
    String id;
    int contador;
    String stock1;
    Integer stock2;
    FragmentTransaction transaction1;
    Fragment fragmentReserva, fragmentInicio1;
    ImageView imageViewfotoDNI;
    Mat rgb;
    MatOfRect rects;
    CascadeClassifier cascadeClassifier;
    ImageView bitmap1;
    Bundle bundle=new Bundle();
    String DNIURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_dispositivos);

        if(OpenCVLoader.initDebug()){
            try {
                InputStream inputStream = getResources().openRawResource(R.raw.lbpcascade_frontalface);
                File file =new File(getDir("cascade",MODE_PRIVATE),"lbpcascade_frontalface.xml");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] data = new byte[4096];
                int read_bytes;

                while ((read_bytes=inputStream.read(data)) !=-1){
                    fileOutputStream.write(data,0,read_bytes);
                }
                cascadeClassifier = new CascadeClassifier(file.getAbsolutePath());
                if(cascadeClassifier.empty()) cascadeClassifier=null;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        fragmentReserva=new BlankFragmentReserva();
        fragmentInicio1=new BlankFragment();

        id =  getIntent().getStringExtra("idEquipo");


        bundle.putString("IDEQUIPO",id);
        fragmentReserva.setArguments(bundle);


        Uri uri =getIntent().getData();


        Bitmap bitmap = getIntent().getParcelableExtra("lalala");




        contador=0;

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        miStorage= FirebaseStorage.getInstance().getReference();
        uidUsuario=mAuth.getCurrentUser().getUid();

        motivo=findViewById(R.id.motivo);
        curso=findViewById((R.id.curso));
        programas=findViewById(R.id.programsNece);
        otros=findViewById(R.id.another);

        cantidad=findViewById(R.id.textViewCantidad);
        reservar=findViewById(R.id.buttonReservar);
        sumar=findViewById(R.id.buttonMas);
        imageViewfotoDNI=findViewById(R.id.imageViewFotodni);



        if(bitmap==null){
        }else {
            rgb= new Mat();
            rects=new MatOfRect();
            Utils.bitmapToMat(bitmap,rgb);
            cascadeClassifier.detectMultiScale(rgb,rects,1.1,2);
            for(Rect rect : rects.toList()){
                Mat submat = rgb.submat(rect);
                Imgproc.blur(submat,submat,new Size(10,10));
                submat.release();
            }
            Utils.matToBitmap(rgb,bitmap);
            imageViewfotoDNI.setImageBitmap(bitmap);
            rgb.release();
        }


        if(uri==null){

        }else{

            if(OpenCVLoader.initDebug()) {
                try {
                    InputStream inputStream = getResources().openRawResource(R.raw.lbpcascade_frontalface);
                    File file = new File(getDir("cascade", MODE_PRIVATE), "lbpcascade_frontalface.xml");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] data = new byte[4096];
                    int read_bytes;

                    while ((read_bytes = inputStream.read(data)) != -1) {
                        fileOutputStream.write(data, 0, read_bytes);
                    }
                    cascadeClassifier = new CascadeClassifier(file.getAbsolutePath());
                    if (cascadeClassifier.empty()) cascadeClassifier = null;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Bitmap bitmapGalery = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    if (bitmapGalery == null) {

                    } else {
                        rgb = new Mat();
                        rects = new MatOfRect();

                        Utils.bitmapToMat(bitmapGalery, rgb);
                        cascadeClassifier.detectMultiScale(rgb, rects, 1.1, 2);
                        for (Rect rect : rects.toList()) {
                            Mat submat = rgb.submat(rect);
                            Imgproc.blur(submat, submat, new Size(100, 100));
                            submat.release();
                        }

                        Utils.matToBitmap(rgb, bitmapGalery);
                        imageViewfotoDNI.setImageBitmap(bitmapGalery);
                        rgb.release();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        bitmap1=imageViewfotoDNI;




        getSupportFragmentManager().beginTransaction().add(R.id.frameReserva,fragmentInicio1).commit();



        cantidad.setText(Integer.toString(contador));

        obtenerEquipo(id);




        sumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stock1=stock.trim();
                stock2=Integer.parseInt(stock1);
                contador ++;
                if(contador>stock2){
                    cantidad.setText(Integer.toString(stock2));
                    Toast.makeText(getApplicationContext(),"Ha superado el limite de dispositivos"+" "+stock1, Toast.LENGTH_SHORT).show();
                }if(contador<=stock2){
                    cantidad.setText(Integer.toString(contador));
                }

            }
        });
        restar=findViewById(R.id.buttonMenos);
        restar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador --;
                cantidad.setText(Integer.toString(contador));
                if(contador<0){
                    contador=0;
                    cantidad.setText(Integer.toString(contador));
                    Toast.makeText(getApplicationContext(),"No ha colocado la cantidad de dispositivos que desea", Toast.LENGTH_SHORT).show();
                }if(contador>=stock2){
                    contador=stock2-1;
                    cantidad.setText(Integer.toString(contador));
                }

            }
        });




        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String motivo2=motivo.getText().toString().trim();
                String curs2=curso.getText().toString().trim();
                String programas2 = programas.getText().toString().trim();
                String cantidad2= cantidad.getText().toString().trim();
                String otros2=otros.getText().toString().trim();
                String tipo2 =tipo.trim();
                String marca2=marca.trim();
                ImageView dni = bitmap1;


                if(motivo2.isEmpty() || curs2.isEmpty() || programas2.isEmpty() || cantidad2.equals("0") || otros2.isEmpty() || dni == null){
                    Toast.makeText(getApplicationContext(),"Ingresar los datos", Toast.LENGTH_SHORT).show();
                }else{
                    putSolicitud(motivo2,curs2,programas2,tipo2,marca2,cantidad2,otros2,dni);

                }
            }
        });


    }


    private void putSolicitud(String motivo2, String curs2, String programas2, String tipo2,String marca2,String cantidad2,String otros2, ImageView dni) {

        String subirStorageSolicitud = storagePath+ "" +storagePath1 +""+photo + "" + mAuth.getCurrentUser().getUid()+"";

        String timeStampDNI = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());

        BitmapDrawable drawable =(BitmapDrawable) dni.getDrawable();
        Bitmap bitmapDNISI=drawable.getBitmap();
        ByteArrayOutputStream baosDNI = new ByteArrayOutputStream();
        bitmapDNISI.compress(Bitmap.CompressFormat.JPEG,100,baosDNI);
        StorageReference filePathDNI = miStorage.child(subirStorageSolicitud).child(timeStampDNI);
        byte[] datas= baosDNI.toByteArray();

        UploadTask uploadTask=filePathDNI.putBytes(datas);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());
                if (uriTask.isSuccessful()){
                    uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DNIURL=uri.toString();

                            Timestamp timestampInicio = Timestamp.now();

                            String timeStampSolicitudFIN ="";

                            Map<String,Object> map= new HashMap<>();
                            map.put("motivo", motivo2);
                            map.put("curso", curs2);
                            map.put("programas", programas2);
                            map.put("tipo", tipo2);
                            map.put("marca", marca2);
                            map.put("estado","pendiente");
                            map.put("uidCliente",uidUsuario);
                            map.put("uidEquipo",id);
                            map.put("tiempoPrestamo",cantidad2);
                            map.put("otros",otros2);
                            map.put("urlFotoDNI",DNIURL);
                            map.put("tiempoInicio",timestampInicio);
                            map.put("tiempoFin",timeStampSolicitudFIN);

                            firebaseFirestore.collection("solicitudes").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(),"Creado de manera exitosa", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(ReservaDispositivosActivity.this,ListaSolicitudesActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"error al ingresar", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void obtenerEquipo(String id){
        firebaseFirestore.collection("equipos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Equipo equipo = documentSnapshot.toObject(Equipo.class);
                tipo = equipo.getTipo();
                marca = equipo.getMarca();
                stock=equipo.getStock();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReservaDispositivosActivity.this,"Error al obtener los datos",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void confe(View view){
        transaction1=getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.imageViewFotodni:transaction1.replace(R.id.frameReserva,fragmentReserva);

                break;
            case R.id.imageButton4Reserva:transaction1.replace(R.id.frameReserva,fragmentInicio1);
                break;
        }
        transaction1.commit();
    }

}