package com.example.practiceforandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PokemonActivity extends AppCompatActivity {

    private Button button, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_activity);

        button = findViewById(R.id.btnPokeLista);
        button.setOnClickListener(v -> {
            onClick(v);
        });

        button2 = findViewById(R.id.btnRegistrarPokemon);
        button2.setOnClickListener(v -> {
            onClick2(v);
        });

    }

    private void onClick(View v) {
        Intent intent = new Intent(this, ListarPokemones.class);
        startActivity(intent);
    }

    private void onClick2(View v) {
        Intent intent2 = new Intent(this, FormPokemonActivity.class);
        startActivity(intent2);
    }
}