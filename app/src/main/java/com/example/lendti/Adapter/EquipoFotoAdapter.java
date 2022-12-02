package com.example.lendti.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lendti.R;

import java.util.ArrayList;
import java.util.List;

public class EquipoFotoAdapter extends RecyclerView.Adapter<EquipoFotoAdapter.ViewHolder> {

    private List<String> listaFotos;
    Activity actividad;

    public EquipoFotoAdapter(List<String> dataset, Activity activity){
        listaFotos =dataset;
        actividad = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_imagen_equipo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image = listaFotos.get(position);
        Glide.with(actividad).load(image).fitCenter().centerCrop().into(holder.imageViewPhoto);
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaFotos.remove(image);
            }
        });
    }



    @Override
    public int getItemCount() {
        return 0;
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
}
