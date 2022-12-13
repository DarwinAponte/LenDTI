package com.example.lendti.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EquipoFotoAdapter extends FirestoreRecyclerAdapter<Imagen,EquipoFotoAdapter.ViewHolder> {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    FirebaseStorage mStorage = FirebaseStorage.getInstance();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public EquipoFotoAdapter(@NonNull FirestoreRecyclerOptions<Imagen> options, Activity actividad){

        super(options);
        this.activity = actividad;
        System.out.println("fotoadapter");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_imagen_equipo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Imagen imagen) {
        System.out.println("apunto de colocar imagen");
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        Glide.with(activity)
                .load(imagen.getUri())
                .into(holder.imageViewPhoto);

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarFoto(id);
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPhoto;
        Button btnEliminar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPhoto = itemView.findViewById(R.id.imageViewEquipo);
            btnEliminar =  itemView.findViewById(R.id.btnEliminarImagen);
        }
    }

    public void eliminarFoto(String id){
        firebaseFirestore.collection("imagenes").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Imagen imagen = documentSnapshot.toObject(Imagen.class);
                firebaseFirestore.collection("imagenes").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        StorageReference reference = mStorage.getReference().child("images/").child(imagen.getPath());
                        reference.delete();
                        Toast.makeText(activity,"Imagen eliminada",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity,"Error al eliminar",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





    }
}
