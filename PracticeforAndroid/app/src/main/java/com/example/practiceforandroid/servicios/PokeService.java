package com.example.practiceforandroid.servicios;

import com.example.practiceforandroid.entidades.PokemonEnt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PokeService {
    //todo esto depende del API( GET SET DELETE )
    @GET("contactos")
    Call<List<PokemonEnt>> getListContact();
    @POST("contactos")
    Call<Void> create(@Body PokemonEnt poke);//guardar datos
    /*@PUT("contacts/{id}")
    Call<Void> update (@Body ContactApi contactApi, @Path("id")int id);*/
    @PUT("contactos/{id}")
    Call<PokemonEnt> update(@Body PokemonEnt poke, @Path("id")int id);
    @DELETE("contactos/{id}")
    Call<PokemonEnt> delete (@Path("id")int id);
}
