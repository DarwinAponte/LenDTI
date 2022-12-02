package com.example.lendti.Dialogos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lendti.R;
import com.example.lendti.UserIT.SolicitudEspecificaActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolicitudRechazoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolicitudRechazoFragment extends DialogFragment{


    Activity actividad;

    Button btnEnviar;
    Button btnCancelar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SolicitudRechazoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SolicitudRechazoFragment newInstance(String param1, String param2) {
        SolicitudRechazoFragment fragment = new SolicitudRechazoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return crearDialogRechazo();
    }

    private AlertDialog crearDialogRechazo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_solicitud_rechazo,null);
        builder.setView(v);

        btnCancelar = v.findViewById(R.id.btnFragmentCancelar);
        btnEnviar = v.findViewById(R.id.btnFragmentEnviar);
        
        eventosBotones();
        return builder.create();

    }

    private void eventosBotones() {
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SolicitudEspecificaActivity solicitudEspecificaActivity = new SolicitudEspecificaActivity();
                solicitudEspecificaActivity.mostrarAlertSoliRechazar();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.actividad = (Activity) context;
        }
    }

}