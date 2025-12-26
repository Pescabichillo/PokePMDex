package com.example.pokepmdex.Pokedex;

import android.app.Notification;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pokepmdex.MainActivity;
import com.example.pokepmdex.NotificationHandler;
import com.example.pokepmdex.R;
import com.example.pokepmdex.SQLite.DB;

import java.nio.charset.StandardCharsets;

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

        String pokemonSpriteURL = intent.getStringExtra("pokemonSpriteURL");

        byte[] imageMap = intent.getByteArrayExtra("pokemonSprite");
        ((ImageView) findViewById(R.id.data_pokemon_sprite)).setImageBitmap(BitmapFactory.decodeByteArray(imageMap, 0, imageMap.length));

        handler = new NotificationHandler(this);

        // Si se añade a favoritos añadimos se envía una notificación
        // y se agrega la información del pokemon necesaria al repositorio local SQLite
        // para poder acceder a ella cuando queramos de manera offline
        Button favorito = findViewById(R.id.data_button_favourites);

        if(intent.getIntExtra("showButtonAddFavourite", 0) == 0) {
            favorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        DB.savePokemon(MainActivity.db, name, height, weight, hp, attack, defense, specialAttack, specialDefense, speed, imageMap, pokemonSpriteURL);
                        String[] notification = getResources().getStringArray(R.array.favourites_notification);
                        Notification.Builder nBuilder = handler.createNotification(notification[0], String.format(notification[1], name.toUpperCase()));
                        handler.getManager().notify(1, nBuilder.build());
                        handler.publishGroup();
                    } catch(SQLiteConstraintException e) {
                        Toast.makeText(PokemonData.this, getString(R.string.favourites_already), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            favorito.setVisibility(View.INVISIBLE);
        }



        // Botón de compartir (Comparte URL a imagen)
        ImageButton share = findViewById(R.id.data_button_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, pokemonSpriteURL);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, null));
            }
        });
    }
}