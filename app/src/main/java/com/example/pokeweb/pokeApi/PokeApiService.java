package com.example.pokeweb.pokeApi;

import retrofit2.Call;

import com.example.pokeweb.models.PokemonResponse;

import retrofit2.http.GET;

public interface PokeApiService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList();
}
