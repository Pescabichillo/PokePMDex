package com.example.pokepmdex;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.example.pokepmdex.Datos.Pokemon;

import java.util.List;

public class PokemonAdapter extends BaseAdapter {

    private List<Pokemon> pokemons;
    private Context ctx;

    private LayoutInflater inflater; // Para inflar nuestro layout personalizado

    public PokemonAdapter(Context ctx, List<Pokemon> datos) {
        this.ctx = ctx;
        this.pokemons = datos;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return pokemons.size();
    }

    @Override
    public Object getItem(int position) {
        return pokemons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.pokemon_list, parent, false);
        }

        Pokemon pokemon = pokemons.get(position);

        TextView text = convertView.findViewById(R.id.PokemonListName);
        String pokemonName = pokemon.getPokemonName() + " #" + pokemon.getPokemonID();
        text.setText(pokemonName);


        ImageView image = convertView.findViewById(R.id.PokemonListSprite);
        image.setImageBitmap(pokemon.getImageMap());

        return convertView;
    }
}
