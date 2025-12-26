package com.example.pokepmdex.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pokemons_favoritos";
    private static final int CURRENT_VERSION = 5;

    public Helper(Context cxt) {
        super(cxt, DATABASE_NAME, null, CURRENT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE pokemons ( " +
                            "nombre TEXT PRIMARY KEY, " +
                            "altura INTEGER, " +
                            "peso INTEGER, " +
                            "Hp INTEGER, " +
                            "Attack INTEGER, " +
                            "Defense INTEGER, " +
                            "SpecialAttack INTEGER, " +
                            "SpecialDefense INTEGER, " +
                            "Speed INTEGER," +
                            "sprite BLOB, " +
                            "spriteURL TEXT" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE pokemons;");
        onCreate(db);
    }
}
