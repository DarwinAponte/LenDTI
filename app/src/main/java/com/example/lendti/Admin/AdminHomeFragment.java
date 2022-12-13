package com.example.lendti.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lendti.Entity.UserIT;
import com.example.lendti.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminHomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private DatabaseReference reference;
    ArrayList<UserIT> listaUserTI;
    MyAdapterUserTI adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin_home, container, false);



        //Para recycleviews
        recyclerView=(RecyclerView)view.findViewById(R.id.recycleViewAdmin);
        reference = FirebaseDatabase.getInstance().getReference("UsuariosTI");
        listaUserTI=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter=new MyAdapterUserTI(view.getContext(),listaUserTI);
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    UserIT userIT=dataSnapshot.getValue(UserIT.class);
                    listaUserTI.add(userIT);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button CrearUserTI=(Button)view.findViewById(R.id.btn_adminHome_AddUserIT);
        CrearUserTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),AdminNewUserActivity.class));
            }
        });


        return view;
    }



}