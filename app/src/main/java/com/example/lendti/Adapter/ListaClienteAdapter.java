package com.example.lendti.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendti.Entity.Solicitud;
import com.example.lendti.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ListaClienteAdapter extends FirestoreRecyclerAdapter<Solicitud,ListaClienteAdapter.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ListaClienteAdapter(@NonNull FirestoreRecyclerOptions<Solicitud> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Solicitud solicitudes) {
        holder.tipo.setText(solicitudes.getTipo());
        holder.marca.setText(solicitudes.getMarca());
        holder.time.setText(solicitudes.getTime());
        holder.estado.setText(solicitudes.getEstado());


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_prestamos,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipo;
        TextView marca;
        TextView time;
        TextView estado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipo=itemView.findViewById(R.id.tipo2);
            marca=itemView.findViewById(R.id.marca2);
            time=itemView.findViewById(R.id.time2);
            estado=itemView.findViewById(R.id.estado2);
        }
    }
}
