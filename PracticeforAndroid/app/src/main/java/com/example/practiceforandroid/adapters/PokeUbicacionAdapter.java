package com.example.practiceforandroid.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceforandroid.PokeMapsActivity;
import com.example.practiceforandroid.R;
import com.example.practiceforandroid.entidades.PokeUbicacionEnt;

import java.util.List;

public class PokeUbicacionAdapter extends RecyclerView.Adapter{

    List<PokeUbicacionEnt> datos;

    public PokeUbicacionAdapter(List<PokeUbicacionEnt> datos) {
        this.datos=datos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_poke_ubicacion,parent,false);
        return new PokeUbicacionAdapter.PokeUbicacionAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final PokeUbicacionAdapter.PokeUbicacionAdapterViewHolder viewHolder = (PokeUbicacionAdapterViewHolder) holder;

        TextView tvLongitud = holder.itemView.findViewById(R.id.tvLongitud);
        tvLongitud.setText(datos.get(position).longitud);

        TextView tvLatitud = holder.itemView.findViewById(R.id.tvLatitud);
        tvLatitud.setText(datos.get(position).latitud);

        viewHolder.btnUbicacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PokeMapsActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    static class PokeUbicacionAdapterViewHolder extends RecyclerView.ViewHolder{

        Button btnUbicacion;
        public PokeUbicacionAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            btnUbicacion = (Button)itemView.findViewById(R.id.btnUbicacion);
        }
    }
}
