package com.example.pokeweb.pokeApi;

import retrofit2.Call;

import com.example.pokeweb.models.Pokemon;
import com.example.pokeweb.models.PokemonResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {
    @GET("pokemon/{id}")
    Call<Pokemon> getPokemonList(@Path("id") int id);
}
