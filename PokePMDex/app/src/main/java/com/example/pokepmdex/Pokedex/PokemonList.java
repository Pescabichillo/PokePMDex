package com.example.pokepmdex.Pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pokepmdex.Datos.Pokemon;
import com.example.pokepmdex.Threads.DescargarPokemons;
import com.example.pokepmdex.Adapter.PokemonAdapter;
import com.example.pokepmdex.R;

import java.util.List;

public class PokemonList extends AppCompatActivity {

    private List<Pokemon> pokemons;
    private int[] generations = {0, 151, 251, 386, 493, 649, 721};
    private boolean firstPage = true;
    private boolean lastPage = false;

    private int offset;

    private Button prev;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ButtonNext), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        int selectedGeneration = intent.getIntExtra("Generation", 0);

        ((TextView) findViewById(R.id.list_number_generation)).setText(String.valueOf(selectedGeneration + 1));

        offset = generations[selectedGeneration];

        prev = findViewById(R.id.main_button_generations);
        next = findViewById(R.id.main_button_favourites);

        prev.setVisibility(View.GONE);
        next.setVisibility(View.GONE);

        ImageButton ret = findViewById(R.id.list_button_return);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Botón next
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int limit;
                offset += 20;
                // Para no mostrar pokemons de otra generación en la generación seleccionada
                if(offset + 20 >= generations[selectedGeneration + 1]) {
                    limit = generations[selectedGeneration + 1] - offset;
                    lastPage = true;
                }
                else {
                    limit = 20;
                }
                // Lanzamos thread de llamada a la api
                DescargarPokemons task = new DescargarPokemons(PokemonList.this, offset, limit);
                new Thread(task).start();
            }
        });

        // Botón previous
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset -= 20;
                if(offset == generations[selectedGeneration]) firstPage = true;
                // Lanzamos thread de llamada a la api
                DescargarPokemons task = new DescargarPokemons(PokemonList.this, offset, 20);
                new Thread(task).start();
            }
        });

        // Lanzamos thread de llamada a la api
        DescargarPokemons task = new DescargarPokemons(this, offset, 20);
        new Thread(task).start();

        // Si le damos a cualquier elemento de la lista nos abre otra actividad con información
        // ampliada del elemento al que hemos pulsado
        GridView gridView = findViewById(R.id.PokemonList);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickedPokemon = new Intent(getApplicationContext(), PokemonData.class);

                // Pasamos información del pokemon por el intent
                clickedPokemon.putExtra("pokemonName", pokemons.get(position).getPokemonName());
                clickedPokemon.putExtra("pokemonHeight", pokemons.get(position).getPokemonHeight());
                clickedPokemon.putExtra("pokemonWeight", pokemons.get(position).getPokemonWeight());
                clickedPokemon.putExtra("pokemonSprite", pokemons.get(position).getImageMap());
                clickedPokemon.putExtra("pokemonSpriteURL", pokemons.get(position).getPokemonFrontDefaultURL());

                clickedPokemon.putExtra("pokemonHp", pokemons.get(position).getPokemonHp());
                clickedPokemon.putExtra("pokemonAttack", pokemons.get(position).getPokemonAttack());
                clickedPokemon.putExtra("pokemonDefense", pokemons.get(position).getPokemonDefense());
                clickedPokemon.putExtra("pokemonSpecialAttack", pokemons.get(position).getPokemonSpecialAttack());
                clickedPokemon.putExtra("pokemonSpecialDefense", pokemons.get(position).getPokemonSpecialDefense());
                clickedPokemon.putExtra("pokemonSpeed", pokemons.get(position).getPokemonSpeed());

                startActivity(clickedPokemon);
            }
        });
    }

    public void startDownload() {
        GridView listaPokemon = findViewById(R.id.PokemonList);
        listaPokemon.setAdapter(null);
        findViewById(R.id.list_progressBar).setVisibility(View.VISIBLE);
        next.setVisibility(View.GONE);
        prev.setVisibility(View.GONE);
    }

    public void finishDownload(List<Pokemon> result) {
        GridView listaPokemon = findViewById(R.id.PokemonList);

        BaseAdapter adapterPokemon= new PokemonAdapter(this, result);

        listaPokemon.setAdapter(adapterPokemon);

        findViewById(R.id.list_progressBar).setVisibility(View.INVISIBLE);

        // Muestra el botón de previous siempre que no estemos en la primera página
        if(firstPage) {
            firstPage = false;
        }
        else {
            prev.setVisibility(View.VISIBLE);
        }

        // Muestra el botón de next siempre que no estemos en la última página
        if(lastPage) {
            lastPage= false;
        }
        else {
            next.setVisibility(View.VISIBLE);
        }
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}