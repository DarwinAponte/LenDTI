package com.example.lendti.Admin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lendti.Entity.UserIT;
import com.example.lendti.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class MyAdapterUserTI extends RecyclerView.Adapter<MyAdapterUserTI.MyViewHolder>{
    public MyAdapterUserTI(Context context, ArrayList<UserIT> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    ArrayList<UserIT> list;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.admin_usuarios_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterUserTI.MyViewHolder holder, int position) {
        UserIT userIT=list.get(position);
        holder.nombre.setText(userIT.getNombre());
        holder.correo.setText(userIT.getCorreo());
        holder.codigo.setText(userIT.getCodigo());
        Log.d("Foto",userIT.getUrlfoto());
        Glide.with(context).load(userIT.getUrlfoto()).into(holder.fotoPerfilUsuarioTI);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, correo, codigo;
        CircularImageView fotoPerfilUsuarioTI;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.textAdminNombreUserTI);
            correo=itemView.findViewById(R.id.textAdmincorreoUserTI);
            codigo=itemView.findViewById(R.id.textAdminCodigoUserTI);
            fotoPerfilUsuarioTI=itemView.findViewById(R.id.fotoPerfilUsuarioTI);
        }
    }
}
