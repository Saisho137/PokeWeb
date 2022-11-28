package com.example.pokeweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.widget.GridLayout;

import com.example.pokeweb.models.Pokemon;
import com.example.pokeweb.models.PokemonResponse;
import com.example.pokeweb.pokeApi.PokeApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokeCompanion extends AppCompatActivity {

    private Retrofit retrofit;

    private static final String TAG = "POKEDEX";

    private RecyclerView recyclerView;
    private PokemonListAdapter pokemonListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_companion);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pokemonListAdapter = new PokemonListAdapter();
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);


        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getData();
    }

    private void getData() {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonResponse> PokemonResponseCall = service.getPokemonList();
        Log.i("WARNING", "CODE COMES HERE.");
        PokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful()) {
                    PokemonResponse pokemonResponse = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonResponse.getResults();

                    pokemonListAdapter.addPokemonList(pokemonList);
                } else {
                    Log.e(TAG, "onResponse" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e(TAG, "onFailure" + t.getMessage());
            }
        });
    }
}