package com.example.lendti.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendti.Entity.Equipo;
import com.example.lendti.Entity.Solicitud;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ListadeSolicitudesAprobadasAdapter extends FirestoreRecyclerAdapter<Solicitud,ListadeSolicitudesAprobadasAdapter.ViewHolder>{


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ListadeSolicitudesAprobadasAdapter(@NonNull FirestoreRecyclerOptions<Solicitud> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewholder, int position, @NonNull Solicitud solicitudes) {
        viewholder.tipo.setText(solicitudes.getTipo());
        viewholder.marca.setText(solicitudes.getMarca());
        viewholder.time.setText(solicitudes.getTiempoInicio().toString());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud_lista, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipo;
        TextView marca;
        TextView time;
        TextView uid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipo = itemView.findViewById(R.id.tipo);
            marca = itemView.findViewById(R.id.marcaAprobados);
            time = itemView.findViewById(R.id.TiempoPrestamoAprobadas);
        }
    }
}
