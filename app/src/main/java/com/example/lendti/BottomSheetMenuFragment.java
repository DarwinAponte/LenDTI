package com.example.lendti;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheetMenuFragment extends BottomSheetDialogFragment {

    private static final String ARG_LAYOUT_ID = "ARG_LAYOUT_ID";
    private static final int GRID = R.layout.fragment_grid_bottom_sheet;
    private Dialog dialog;
    private int style;
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    private static final int GALLERY_INTENT = 1;

    public static BottomSheetMenuFragment createInstanceGrid() {
        return getInstance(GRID);
    }

    private static BottomSheetMenuFragment getInstance(int layoutId) {
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_ID, layoutId);
        BottomSheetMenuFragment frag = new BottomSheetMenuFragment();
        frag.setArguments(args);
        return frag;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        //noinspection RestrictedApi
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), getArguments().getInt(ARG_LAYOUT_ID), null);
        ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);
    }

    @OnClick(R.id.imageButtonCamara)
    public void onClickBottomCamara(View view) {


    }

    @OnClick(R.id.imageButtonGaleria)
    public void onClickBottomGaleria(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            Uri uri = data.getData();
            StorageReference filePath = mStorage.child("fotos").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri descargarfoto = taskSnapshot.getStorage().getDownloadUrl().getResult();

                    Toast.makeText(getActivity(),"Se subio exitosamente",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
