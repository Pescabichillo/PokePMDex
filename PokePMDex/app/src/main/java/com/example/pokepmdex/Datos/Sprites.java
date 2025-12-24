package com.example.pokepmdex.Datos;

import com.google.gson.annotations.SerializedName;

public class Sprites {

    @SerializedName("front_default")
    private final String frontDefaultURL;

    @SerializedName("front_shiny")
    private final String frontShinyURL;

    public Sprites(String frontDefaultURL, String frontShinyURL) {
        this.frontDefaultURL = frontDefaultURL;
        this.frontShinyURL = frontShinyURL;
    }

    public String getFrontDefaultURL() {
        return frontDefaultURL;
    }

    public String getFrontShinyURL() {
        return frontShinyURL;
    }
}
