package com.example.pokepmdex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class PokemonData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();

        DescargarImagen task = new DescargarImagen(findViewById(R.id.PokemonSprite), intent.getStringExtra("pokemonSpriteURL"));
        Thread thread = new Thread(task);

        thread.start();

        TextView pokemonName = findViewById(R.id.PokemonNameData);
        pokemonName.setText(intent.getStringExtra("pokemonName"));

        TextView pokemonHeight = findViewById(R.id.PokemonHeightData);
        pokemonHeight.setText(String.valueOf(intent.getIntExtra("pokemonHeight", 0)) + " cm");

        TextView pokemonWeight = findViewById(R.id.PokemonWeightData);
        pokemonWeight.setText(String.valueOf(intent.getIntExtra("pokemonWeight", 0)) + " kg");

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}