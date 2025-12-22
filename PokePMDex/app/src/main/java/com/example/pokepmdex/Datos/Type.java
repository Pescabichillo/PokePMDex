package com.example.pokepmdex.Datos;

import com.google.gson.annotations.SerializedName;

class Type {
    @SerializedName("name")
    private final String type;

    public Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
