package com.example.lendti.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.example.lendti.UserIT.AgregarEquipoActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EquipoAdapter extends FirestoreRecyclerAdapter<Equipo,EquipoAdapter.ViewHolder> {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EquipoAdapter(@NonNull FirestoreRecyclerOptions<Equipo> options,Activity activity) {
        super(options);
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listanormal,parent,false);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Equipo equipo) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        holder.tipo.setText("Tipo: " + equipo.getTipo());
        holder.marca.setText("Marca: " + equipo.getMarca());
        holder.stock.setText("Stock: "+ equipo.getStock()+" unidades");

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, AgregarEquipoActivity.class);
                i.putExtra("idEquipo",id);
                activity.startActivity(i);
            }
        });

        holder.btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity,AgregarEquipoActivity.class);
                i.putExtra("idEquipo",id);
                i.putExtra("ver","ver");
                activity.startActivity(i);
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarEquipo(id);
            }
        });
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipo;
        TextView marca;
        TextView stock;
        ImageButton btnEliminar;
        ImageButton btnEditar;
        ImageButton btnVer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btnEditar = itemView.findViewById(R.id.imageButtonEdit);
            btnEliminar = itemView.findViewById(R.id.imageButtonBorrar);
            btnVer = itemView.findViewById(R.id.imageButtonVerMas);
            tipo = itemView.findViewById(R.id.textViewTipo);
            marca = itemView.findViewById(R.id.textViewMarca);
            stock = itemView.findViewById(R.id.textViewStock);
        }
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
