package com.example.lendti.Adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.example.lendti.UserIT.AgregarEquipoActivity;
import com.example.lendti.UserIT.ListaEquipoActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class EquipoITAdapter extends FirestoreRecyclerAdapter<Equipo, EquipoITAdapter.ViewHolder> {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EquipoITAdapter(@NonNull FirestoreRecyclerOptions<Equipo> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listanormal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Equipo equipo) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        holder.tipo.setText("Tipo: " + equipo.getTipo());
        holder.marca.setText("Marca: " + equipo.getMarca());
        holder.stock.setText("Stock: " + equipo.getStock() + " unidades");
        Glide.with(activity).load(equipo.getListaFotos().get(0)).centerCrop().into(holder.foto);


        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, AgregarEquipoActivity.class);
                i.putExtra("idEquipo", id);

                activity.startActivity(i);
            }
        });

        holder.btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, AgregarEquipoActivity.class);
                i.putExtra("idEquipo", id);
                i.putExtra("ver", "ver");
                activity.startActivity(i);
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mostrarAlertEquipEliminar(id);
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipo;
        TextView marca;
        TextView stock;
        ImageView foto;
        ImageButton btnEliminar;
        ImageButton btnEditar;
        ImageButton btnVer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.imageViewFoto);
            btnEditar = itemView.findViewById(R.id.imageButtonEdit);
            btnEliminar = itemView.findViewById(R.id.imageButtonBorrar);
            btnVer = itemView.findViewById(R.id.imageButtonVerMas);
            tipo = itemView.findViewById(R.id.textViewTipo);
            marca = itemView.findViewById(R.id.textViewMarca);
            stock = itemView.findViewById(R.id.textViewStock);
        }
    }


    public void mostrarAlertEquipEliminar(String id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setMessage("¿Desea eliminar el equipo de la lista?");
        alertDialog.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarEquipo(id);
                        activity.startActivity(new Intent(activity, ListaEquipoActivity.class));
                    }
                });
        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        alertDialog.show();
    }

    public void eliminarEquipo(String id){
        firebaseFirestore.collection("equipos").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity,"Eliminado correctamente",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity,"Error al eliminar",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
