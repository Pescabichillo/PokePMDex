package com.example.pokepmdex.SQLite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pokepmdex.Datos.BaseStats;
import com.example.pokepmdex.Datos.Pokemon;
import com.example.pokepmdex.Datos.Sprites;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class DB {

    public static List<Pokemon> getAllPokemon(Helper helper) {
        String query = "SELECT * FROM pokemons ORDER BY nombre;";
        List<Pokemon> resultado = new LinkedList<>();

        SQLiteDatabase conn =  helper.getReadableDatabase();
        Cursor c = conn.rawQuery(query, null);

        c.moveToFirst();
        while(!c.isAfterLast()) {
            List<BaseStats> baseStats = new LinkedList<>();
            // Hp
            baseStats.add(new BaseStats(c.getInt(3)));
            // Attack
            baseStats.add(new BaseStats(c.getInt(4)));
            // Defense
            baseStats.add(new BaseStats(c.getInt(5)));
            // Special Attack
            baseStats.add(new BaseStats(c.getInt(6)));
            // Special Defense
            baseStats.add(new BaseStats(c.getInt(7)));
            // Speed
            baseStats.add(new BaseStats(c.getInt(8)));

            Sprites sprite = new Sprites(c.getString(10), null);

            Pokemon pokemon = new Pokemon(c.getString(0), 0, c.getInt(1), c.getInt(2), baseStats, sprite, null);
            pokemon.setImageMap(c.getBlob(9));
            resultado.add(pokemon);
            c.moveToNext();
        }
        c.close();
        conn.close();
        return resultado;
    }

    // Guarda un pokemon en la lista
    public static void savePokemon(Helper helper, String name, int altura, int peso, int Hp, int Attack, int Defense, int SpecialAttack, int SpecialDefense, int Speed, byte[] imageMap, String spriteURL) {
        String insert = "INSERT INTO pokemons (nombre, altura, peso, Hp, Attack, Defense, SpecialAttack, SpecialDefense, Speed, sprite, spriteURL) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        SQLiteDatabase conn = helper.getWritableDatabase();
        conn.execSQL(insert, new Object[]{ name, String.valueOf(altura), String.valueOf(peso), String.valueOf(Hp), String.valueOf(Attack), String.valueOf(Defense), String.valueOf(SpecialAttack), String.valueOf(SpecialDefense), String.valueOf(Speed), imageMap, spriteURL });
        conn.close();
    }

    // Elimina un pokemon de la lista
    public static void deletePokemon(Helper helper, String name) {
        String delete = "DELETE FROM pokemons WHERE nombre IS ? ;";
        SQLiteDatabase conn = helper.getWritableDatabase();
        conn.execSQL(delete, new String[] { name });
        conn.close();
    }
}
