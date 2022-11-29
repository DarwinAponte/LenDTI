package com.example.lendti.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.lendti.R;
import com.example.lendti.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new AdminHomeFragment());

        binding.bottomNavigationViewAdmmin.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.btn_bar_home:
                    replaceFragment(new AdminHomeFragment());
                    break;
                case R.id.btn_bar_equipo:
                    replaceFragment(new AdminEquiposFragment());
                    break;
                case R.id.btn_bar_prestamos:
                    replaceFragment(new AdminPrestamoFragment());
                    break;
                case R.id.btn_bar_ranking:
                    replaceFragment(new AdminRankingFragment());
                    break;
                case R.id.btn_bar_perfil:
                    replaceFragment(new AdminPerfilFragment());
                    break;
            }

            return true;
        });


    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutAdmin,fragment);
        fragmentTransaction.commit();

    }
}