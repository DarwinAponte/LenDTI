package com.example.lendti;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheetMenuFragment extends BottomSheetDialogFragment {

    private static final String ARG_LAYOUT_ID = "ARG_LAYOUT_ID";
    private static final int GRID = R.layout.fragment_grid_bottom_sheet;
    private Dialog dialog;
    private int style;

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

    @OnClick({R.id.imageButtonCamara, R.id.imageButtonGaleria})
    public void onClickBottomSheet(View view) {
        Toast.makeText(getContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
        dismiss();
    }

}
