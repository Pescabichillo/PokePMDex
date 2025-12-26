package com.example.pokepmdex.Pokedex;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.pokepmdex.Adapter.PokemonFavouriteAdapter;
import com.example.pokepmdex.Datos.Pokemon;
import com.example.pokepmdex.MainActivity;
import com.example.pokepmdex.R;
import com.example.pokepmdex.SQLite.DB;

import java.util.List;

public class Favourites extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ListView listaPokemon = view.findViewById(R.id.favourites_pokemonList);

        // Obtenemos los pokemons de lista de pokemons favoritos del repositorio local SQLite
        // y lo mostramos en un custom adapter sobre una lista
        List<Pokemon> pokemons = DB.getAllPokemon(MainActivity.db);
        BaseAdapter adapterPokemon= new PokemonFavouriteAdapter(view.getContext(), pokemons);
        listaPokemon.setAdapter(adapterPokemon);

        listaPokemon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ((PokemonFavouriteAdapter) listaPokemon.getAdapter()).removePokemon(position);
                return false;
            }
        });

        listaPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickedPokemon = new Intent(view.getContext(), PokemonData.class);
                clickedPokemon.putExtra("showButtonAddFavourite", 1);

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

        // Para hacer filtros de busqueda con el nombre
        EditText busqueda = view.findViewById(R.id.favourites_buscar);
        busqueda.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                ((PokemonFavouriteAdapter) listaPokemon.getAdapter()).setFilter(busqueda.getText().toString().toUpperCase());
                return false;
            }
        });
    }
}