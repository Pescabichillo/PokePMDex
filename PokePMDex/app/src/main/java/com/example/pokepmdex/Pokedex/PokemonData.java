package com.example.pokepmdex.Pokedex;

import android.app.Notification;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pokepmdex.Threads.DescargarImagen;
import com.example.pokepmdex.MainActivity;
import com.example.pokepmdex.NotificationHandler;
import com.example.pokepmdex.R;
import com.example.pokepmdex.SQLite.DB;

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

        int height = intent.getIntExtra("pokemonHeight", 0);
        ((TextView) findViewById(R.id.data_pokemon_height)).setText(String.valueOf(((float) height)/10) + " m");
        int weight = intent.getIntExtra("pokemonWeight", 0);
        ((TextView) findViewById(R.id.data_pokemon_weight)).setText(String.valueOf(((float) weight)/10) + " kg");

        int hp = intent.getIntExtra("pokemonHp", 0);
        ((TextView) findViewById(R.id.data_pokemon_hp)).setText(String.valueOf(hp));
        int attack = intent.getIntExtra("pokemonAttack", 0);
        ((TextView) findViewById(R.id.data_pokemon_attack)).setText(String.valueOf(attack));
        int defense = intent.getIntExtra("pokemonDefense", 0);
        ((TextView) findViewById(R.id.data_pokemon_defense)).setText(String.valueOf(defense));
        int specialAttack = intent.getIntExtra("pokemonSpecialAttack", 0);
        ((TextView) findViewById(R.id.data_pokemon_special_attack)).setText(String.valueOf(specialAttack));
        int specialDefense = intent.getIntExtra("pokemonSpecialDefense", 0);
        ((TextView) findViewById(R.id.data_pokemon_special_defense)).setText(String.valueOf(specialDefense));
        int speed = intent.getIntExtra("pokemonSpeed", 0);
        ((TextView) findViewById(R.id.data_pokemon_speed)).setText(String.valueOf(speed));

        byte[] imageMap = intent.getByteArrayExtra("pokemonSprite");
        ((ImageView) findViewById(R.id.data_pokemon_sprite)).setImageBitmap(BitmapFactory.decodeByteArray(imageMap, 0, imageMap.length));

        handler = new NotificationHandler(this);

        Button favorito = findViewById(R.id.data_button_favourites);
        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DB.savePokemon(MainActivity.db, name, height, weight, hp, attack, defense, specialAttack, specialDefense, speed, imageMap);
                    Notification.Builder nBuilder = handler.createNotification("Pokémon añadido a favoritos", "Has añadido a " + name.toUpperCase() + " a tu lista de favoritos");
                    handler.getManager().notify(1, nBuilder.build());
                    handler.publishGroup();
                } catch(SQLiteConstraintException e) {
                    Toast.makeText(PokemonData.this, "Pokemon ya en favoritos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}