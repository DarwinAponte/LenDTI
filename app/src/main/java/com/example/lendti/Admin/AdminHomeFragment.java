package com.example.lendti.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lendti.R;
import com.getbase.floatingactionbutton.FloatingActionButton;


public class AdminHomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin_home, container, false);

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