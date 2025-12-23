package com.example.pokepmdex;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PokemonData extends AppCompatActivity {

    private NotificationHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ButtonNext), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();

        // Para descargar la imagen del pokemon
        DescargarImagen task = new DescargarImagen(findViewById(R.id.data_pokemon_sprite), intent.getStringExtra("pokemonSpriteURL"));
        Thread thread = new Thread(task);
        thread.start();

        ImageButton ret = findViewById(R.id.data_button_return);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Obtenemos los datos del pokemon que nos pasa la actividad anterior y los ponemos en sus textos
        // correspondientes
        String name = intent.getStringExtra("pokemonName");
        ((TextView) findViewById(R.id.data_pokemon_name)).setText(name);

        ((TextView) findViewById(R.id.data_pokemon_height)).setText(String.valueOf(intent.getFloatExtra("pokemonHeight", 0)) + " m");
        ((TextView) findViewById(R.id.data_pokemon_weight)).setText(String.valueOf(intent.getFloatExtra("pokemonWeight", 0)) + " kg");

        ((TextView) findViewById(R.id.data_pokemon_hp)).setText(String.valueOf(intent.getIntExtra("pokemonHp", 0)));
        ((TextView) findViewById(R.id.data_pokemon_attack)).setText(String.valueOf(intent.getIntExtra("pokemonAttack", 0)));
        ((TextView) findViewById(R.id.data_pokemon_defense)).setText(String.valueOf(intent.getIntExtra("pokemonDefense", 0)));
        ((TextView) findViewById(R.id.data_pokemon_special_attack)).setText(String.valueOf(intent.getIntExtra("pokemonSpecialAttack", 0)));
        ((TextView) findViewById(R.id.data_pokemon_special_defense)).setText(String.valueOf(intent.getIntExtra("pokemonSpecialDefense", 0)));
        ((TextView) findViewById(R.id.data_pokemon_speed)).setText(String.valueOf(intent.getIntExtra("pokemonSpeed", 0)));

        handler = new NotificationHandler(this);

        CheckBox favorito = findViewById(R.id.data_checkBox_favoritos);
        favorito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                Notification.Builder nBuilder;
                if(isChecked) {
                    nBuilder = handler.createNotification("Pokémon añadido a favoritos", "Has añadido a " + name.toUpperCase() + " a tu lista de favoritos");
                }
                else {
                    nBuilder = handler.createNotification("Pokémon añadido a favoritos", "Has quitado a " + name.toUpperCase() + " de tu lista de favoritos");
                }
                handler.getManager().notify(1, nBuilder.build());
                handler.publishGroup();
            }
        });

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}