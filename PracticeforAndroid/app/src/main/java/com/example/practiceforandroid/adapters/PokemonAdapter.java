package com.example.practiceforandroid.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceforandroid.PokeUbicacionActivity;
import com.example.practiceforandroid.R;
import com.example.practiceforandroid.entidades.PokemonEnt;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter{
    List<PokemonEnt> datos;
    Button button;

    public PokemonAdapter(List<PokemonEnt> datos) {
        this.datos=datos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_pokemon,parent,false);
        return new PokemonAdapter.PokemonAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final PokemonAdapter.PokemonAdapterViewHolder viewHolder = (PokemonAdapterViewHolder) holder;
        TextView tvnombre = holder.itemView.findViewById(R.id.tvnombre);
        tvnombre.setText(datos.get(position).nombre);

        TextView tvTipo = holder.itemView.findViewById(R.id.tvTipo);
        tvTipo.setText(datos.get(position).type);

        ImageView ivImg = holder.itemView.findViewById(R.id.ivPokeImg);
        Picasso.get().load(datos.get(position).imgURL).into(ivImg);

        button = holder.itemView.findViewById(R.id.btnRegistrarPokemon);
        viewHolder.btnUbicacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PokeUbicacionActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    static class PokemonAdapterViewHolder extends RecyclerView.ViewHolder{

        Button btnUbicacion;

        public PokemonAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            btnUbicacion = (Button)itemView.findViewById(R.id.btnUbicacion);
        }
    }

}