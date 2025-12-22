package com.example.pokepmdex.Datos;

import com.google.gson.annotations.SerializedName;

public class BaseStats {
    @SerializedName("base_stat")
    private final int pokemonStat;

    public BaseStats(int pokemonStat) {
        this.pokemonStat = pokemonStat;
    }

    public int getPokemonStat() {
        return pokemonStat;
    }
}
