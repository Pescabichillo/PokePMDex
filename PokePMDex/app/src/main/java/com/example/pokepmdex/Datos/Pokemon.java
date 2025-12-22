package com.example.pokepmdex.Datos;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pokemon {

    @SerializedName("name")
    private final String pokemonName;

    @SerializedName("id")
    private final int pokemonID;

    @SerializedName("height")
    private final int pokemonHeight;

    @SerializedName("weight")
    private final int pokemonWeight;

    @SerializedName("stats")
    private final List<BaseStats> pokemonBaseStats;

    @SerializedName("sprites")
    private final Sprites pokemonSprites;

    @SerializedName("types")
    private final List<Types> types;

    private Bitmap imageMap;

    public Pokemon(String pokemonName, int pokemonID, int pokemonHeight, int pokemonWeight, List<BaseStats> pokemonBaseStats, Sprites pokemonSprites, List<Types> types) {
        this.pokemonName = pokemonName;
        this.pokemonID = pokemonID;
        this.pokemonHeight = pokemonHeight;
        this.pokemonWeight = pokemonWeight;
        this.pokemonBaseStats = pokemonBaseStats;
        this.pokemonSprites = pokemonSprites;
        this.types = types;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public int getPokemonID() {
        return pokemonID;
    }

    public int getPokemonHeight() {
        return pokemonHeight;
    }

    public int getPokemonWeight() {
        return pokemonWeight;
    }

    public int getPokemonHp() {
        return pokemonBaseStats.get(0).getPokemonStat();
    }

    public int getPokemonAttack() {
        return pokemonBaseStats.get(1).getPokemonStat();
    }

    public int getPokemonDefense() {
        return pokemonBaseStats.get(2).getPokemonStat();
    }

    public int getPokemonSpecialAttack() {
        return pokemonBaseStats.get(3).getPokemonStat();
    }

    public int getPokemonSpecialDefense() {
        return pokemonBaseStats.get(4).getPokemonStat();
    }

    public int getPokemonSpeed() {
        return pokemonBaseStats.get(5).getPokemonStat();
    }

    public String getPokemonFrontDefaultURL() {
        return pokemonSprites.getFrontDefaultURL();
    }

    public String getPokemonType1() {
        return types.get(0).getType().getType();
    }

    public String getPokemonType2() {
        try {
            return types.get(1).getType().getType();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Bitmap getImageMap() {
        return imageMap;
    }

    public void setImageMap(Bitmap imageMap) {
        this.imageMap = imageMap;
    }
}
