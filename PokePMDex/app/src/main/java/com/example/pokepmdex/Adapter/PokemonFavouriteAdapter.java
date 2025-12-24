package com.example.pokepmdex.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokepmdex.Datos.Pokemon;
import com.example.pokepmdex.MainActivity;
import com.example.pokepmdex.R;
import com.example.pokepmdex.SQLite.DB;

import java.util.LinkedList;
import java.util.List;

public class PokemonFavouriteAdapter extends BaseAdapter {

    private List<Pokemon> pokemons;

    private List<Pokemon> pokemonsVista;

    private LayoutInflater inflater; // Para inflar nuestro layout personalizado

    private String filter = "";

    public PokemonFavouriteAdapter(Context ctx, List<Pokemon> pokemons) {
        this.pokemons = pokemons;
        this.inflater = LayoutInflater.from(ctx);
        applyFilter();
    }

    @Override
    public int getCount() {
        return pokemonsVista.size();
    }

    @Override
    public Object getItem(int position) {
        return pokemonsVista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.pokemon_favourite_list, parent, false);
        }

        Pokemon pokemon = pokemonsVista.get(position);

        TextView text = convertView.findViewById(R.id.PokemonFavouriteListName);
        text.setText(pokemon.getPokemonName());

        ImageView image = convertView.findViewById(R.id.PokemonFavouriteListSprite);
        byte[] imageMap = pokemon.getImageMap();
        image.setImageBitmap(BitmapFactory.decodeByteArray(imageMap, 0, imageMap.length));

        return convertView;
    }

    public void setFilter(String string) {
        filter = string;
        applyFilter();
    }

    public void removePokemon(int i) {
        DB.deletePokemon(MainActivity.db, pokemons.get(i).getPokemonName());
        pokemons.remove(i);
        applyFilter();
    }

    public void applyFilter() {
        pokemonsVista = new LinkedList<>();
        for(Pokemon p: pokemons) {
            if(p.getPokemonName().toUpperCase().startsWith(filter)) {
                pokemonsVista.add(p);
            }
        }
        notifyDataSetChanged();
    }
}
