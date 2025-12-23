package com.example.pokepmdex;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.pokepmdex.Datos.Pokemon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DescargarPokemons implements Runnable {

    private Context ctx;

    private int offset;
    private int limit;

    public DescargarPokemons(Context cxt, int offset, int limit) {
        this.ctx = cxt;
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public void run() {
        try {
            ((Activity) ctx).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((PokemonList) ctx).startDownload();
                }
            });

            JSONObject obj = new JSONObject(NetUtils.getURLText("https://pokeapi.co/api/v2/pokemon?offset=" + offset + "&limit=" + limit));
            JSONArray arr = obj.getJSONArray("results");

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("dd/MM/yyyy hh:mm a");
            Gson gson = gsonBuilder.create();

            List<Pokemon> pokemons = new ArrayList<>();

            for(int i = 0; i < arr.length(); i++) {
                String resJSON = NetUtils.getURLText(arr.getJSONObject(i).getString("url"));
                pokemons.add(gson.fromJson(resJSON, Pokemon.class));
            }

            for(Pokemon p : pokemons) {
                Bitmap b = NetUtils.getURLBitmap(p.getPokemonFrontDefaultURL());
                p.setImageMap(b);
            }

            ((PokemonList) ctx).setPokemons(pokemons);

            ((Activity) ctx).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((PokemonList) ctx).finishDownload(pokemons);
                }
            });

        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
