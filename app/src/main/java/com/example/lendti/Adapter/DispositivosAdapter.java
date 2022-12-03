package com.example.lendti.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendti.Client.SolicitudDevicesActivity;
import com.example.lendti.Entity.Equipo;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DispositivosAdapter extends FirestoreRecyclerAdapter<Equipo,DispositivosAdapter.ViewHolder> {
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DispositivosAdapter(@NonNull FirestoreRecyclerOptions<Equipo> options, Activity activity) {
        super(options);
        this.activity=activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Equipo equipo) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        holder.tipo.setText(equipo.getTipo());
        holder.marca.setText(equipo.getMarca());

        holder.btnDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,SolicitudDevicesActivity.class);
                intent.putExtra("idEquipo",id);
                intent.putExtra("ver","ver");
                activity.startActivity(intent);


            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_dispositivo,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipo;
        TextView marca;
        Button btnDetalles;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipo=itemView.findViewById(R.id.Dtipo);
            marca=itemView.findViewById(R.id.Dmarca);
            btnDetalles=itemView.findViewById(R.id.buttonDetalles);
        }
    }
}
