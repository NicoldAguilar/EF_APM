package com.example.practiceforandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.practiceforandroid.adapters.PokemonAdapter;
import com.example.practiceforandroid.entidades.PokemonEnt;
import com.example.practiceforandroid.servicios.PokeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarPokemones extends AppCompatActivity {

    private RecyclerView rvContact;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pokemones);

        button = findViewById(R.id.btnRturn);
        button.setOnClickListener(v -> {
            onClick(v);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://63023872c6dda4f287b57f7c.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokeService services = retrofit.create(PokeService.class);
        services.getListContact().enqueue(new Callback<List<PokemonEnt>>() {
            @Override
            public void onResponse(Call<List<PokemonEnt>> call, Response<List<PokemonEnt>> response) {
                List<PokemonEnt> datos = response.body();
                rvContact=findViewById(R.id.rvPokeLista);
                rvContact.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvContact.setAdapter(new PokemonAdapter(datos));
                Log.i("MAIN_APP", "funciona");
            }

            @Override
            public void onFailure(Call<List<PokemonEnt>> call, Throwable t) {
                Log.i("MAIN_APP", "no funciona");
            }
        });
    }

    private void onClick(View v) {
        Intent intent = new Intent(this, PokemonActivity.class);
        startActivity(intent);

    }

}