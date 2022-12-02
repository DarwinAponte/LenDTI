package com.example.lendti.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendti.Entity.Solicitud;
import com.example.lendti.R;
import com.example.lendti.UserIT.SolicitudEspecificaActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SolicitudITAdapter extends FirestoreRecyclerAdapter<Solicitud, SolicitudITAdapter.ViewHolder> {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SolicitudITAdapter(@NonNull FirestoreRecyclerOptions<Solicitud> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull SolicitudITAdapter.ViewHolder holder, int position, @NonNull Solicitud solicitud) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        holder.cliente.setText("Cliente: "+ solicitud.getCliente().getNombre() +" "+solicitud.getCliente().getApellido());
        holder.tipo.setText("Tipo: " + solicitud.getEquipo().getTipo());
        holder.marca.setText("Marca: " + solicitud.getEquipo().getMarca());

        holder.ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, SolicitudEspecificaActivity.class);
                i.putExtra("idSolicitud",id);
                activity.startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public SolicitudITAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud_normal,parent,false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cliente;
        TextView tipo;
        TextView marca;
        TextView ver;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ver = itemView.findViewById(R.id.tvVerCompleto);
            cliente = itemView.findViewById(R.id.tvClienteSolicitud);
            tipo =  itemView.findViewById(R.id.tvTipoSolicitud);
            marca = itemView.findViewById(R.id.tvMarcaSolicitud);
        }
    }
}
