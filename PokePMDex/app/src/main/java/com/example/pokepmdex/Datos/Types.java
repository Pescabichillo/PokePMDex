package com.example.pokepmdex.Datos;

import com.google.gson.annotations.SerializedName;

class Types {
    @SerializedName("type")
    private final Type type;

    public Types(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
