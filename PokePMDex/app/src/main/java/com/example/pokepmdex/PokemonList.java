package com.example.pokepmdex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pokepmdex.Datos.Pokemon;

import java.util.List;

public class PokemonList extends AppCompatActivity {

    private List<Pokemon> pokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DescargarPokemons task = new DescargarPokemons(this, 0);
        new Thread(task).start();

        GridView gridView = findViewById(R.id.PokemonList);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickedPokemon = new Intent(getApplicationContext(), PokemonData.class);

                clickedPokemon.putExtra("pokemonName", pokemons.get(position).getPokemonName());
                clickedPokemon.putExtra("pokemonHeight", pokemons.get(position).getPokemonHeight());
                clickedPokemon.putExtra("pokemonWeight", pokemons.get(position).getPokemonWeight());
                clickedPokemon.putExtra("pokemonSpriteURL", pokemons.get(position).getPokemonFrontDefaultURL());

                startActivity(clickedPokemon);
            }
        });
    }

    public void finishDownload(List<Pokemon> result) {
        // Notificar que la descarga ha terminado

        // Actualizamos la lista en el UI -> ListView con Adapter
        Toast.makeText(this, "Terminada descarga" + result, Toast.LENGTH_SHORT).show();
        GridView listaPlaneta = findViewById(R.id.PokemonList);

        BaseAdapter adapterPlaneta = new PokemonAdapter(this, result);

        listaPlaneta.setAdapter(adapterPlaneta);
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}