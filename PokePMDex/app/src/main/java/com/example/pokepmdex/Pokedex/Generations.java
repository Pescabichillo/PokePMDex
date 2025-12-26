package com.example.pokepmdex.Pokedex;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.pokepmdex.R;

public class Generations extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Intent intent = new Intent(view.getContext(), PokemonList.class);

        view.findViewById(R.id.generations_button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Generation", 0);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.generations_button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Generation", 1);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.generations_button_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Generation", 2);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.generations_button_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Generation", 3);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.generations_button_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Generation", 4);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.generations_button_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Generation", 5);
                startActivity(intent);
            }
        });
    }
}