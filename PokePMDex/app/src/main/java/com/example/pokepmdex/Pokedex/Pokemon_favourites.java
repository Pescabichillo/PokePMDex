package com.example.pokepmdex.Pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pokepmdex.Adapter.PokemonAdapter;
import com.example.pokepmdex.Adapter.PokemonFavouriteAdapter;
import com.example.pokepmdex.Datos.Pokemon;
import com.example.pokepmdex.MainActivity;
import com.example.pokepmdex.R;
import com.example.pokepmdex.SQLite.DB;

import java.util.List;

public class Pokemon_favourites extends AppCompatActivity {

    List<Pokemon> pokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_favourites);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listaPokemon = findViewById(R.id.favourites_pokemonList);

        pokemons = DB.getAllPokemon(MainActivity.db);
        BaseAdapter adapterPokemon= new PokemonFavouriteAdapter(this, pokemons);
        listaPokemon.setAdapter(adapterPokemon);

        ImageButton ret = findViewById(R.id.favourites_button_return);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ListView lista = findViewById(R.id.favourites_pokemonList);
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickedPokemon = new Intent(getApplicationContext(), PokemonData.class);

                clickedPokemon.putExtra("pokemonName", pokemons.get(position).getPokemonName());
                clickedPokemon.putExtra("pokemonHeight", pokemons.get(position).getPokemonHeight());
                clickedPokemon.putExtra("pokemonWeight", pokemons.get(position).getPokemonWeight());
                clickedPokemon.putExtra("pokemonSprite", pokemons.get(position).getImageMap());

                clickedPokemon.putExtra("pokemonHp", pokemons.get(position).getPokemonHp());
                clickedPokemon.putExtra("pokemonAttack", pokemons.get(position).getPokemonAttack());
                clickedPokemon.putExtra("pokemonDefense", pokemons.get(position).getPokemonDefense());
                clickedPokemon.putExtra("pokemonSpecialAttack", pokemons.get(position).getPokemonSpecialAttack());
                clickedPokemon.putExtra("pokemonSpecialDefense", pokemons.get(position).getPokemonSpecialDefense());
                clickedPokemon.putExtra("pokemonSpeed", pokemons.get(position).getPokemonSpeed());

                startActivity(clickedPokemon);
                return false;
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((PokemonFavouriteAdapter) listaPokemon.getAdapter()).removePokemon(position);
            }
        });

        EditText busqueda = findViewById(R.id.favourites_buscar);
        busqueda.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                ((PokemonFavouriteAdapter) listaPokemon.getAdapter()).setFilter(busqueda.getText().toString().toUpperCase());
                return false;
            }
        });
    }

    public void refreshList() {
        ListView lv = findViewById(R.id.favourites_pokemonList);
        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pokemons));
    }
}