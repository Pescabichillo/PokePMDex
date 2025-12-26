package com.example.pokepmdex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.pokepmdex.Pokedex.Favourites;
import com.example.pokepmdex.Pokedex.Generations;
import com.example.pokepmdex.SQLite.Helper;

public class MainActivity extends AppCompatActivity {

    public static Helper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ButtonNext), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new Helper(this);

        // Verificar y solicitar permiso de notificaciones en Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            101
                    );
                }
            }
        }

        FragmentManager fm = getSupportFragmentManager();
        Intent intent = getIntent();

        if(intent.getIntExtra("FragmentFavourite", 0) == 1) {
            Fragment fragment = new Favourites();
            fm.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainerView4,fragment)
                    .commit();
        }

        Button button = findViewById(R.id.main_button_generations);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Generations();
                fm.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainerView4,fragment)
                        .commit();
            }
        });

        Button button2 = findViewById(R.id.main_button_favourites);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Favourites();
                fm.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainerView4,fragment)
                        .commit();
            }
        });
    }
}