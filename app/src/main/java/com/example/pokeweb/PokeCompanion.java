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
        Call<Pokemon> PokemonCall = service.getPokemonList(1);
        Log.i("WARNING", "CODE COMES HERE.");
        PokemonCall.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()) {
                    Pokemon pokemonResponse = response.body();
                    ArrayList<Pokemon> pokemonList = new ArrayList<>();
                    pokemonList.add(pokemonResponse);
                    pokemonListAdapter.addPokemonList(pokemonList);
                    Log.i("Response","Contenido: " + pokemonResponse.getName() +" id: " + pokemonResponse.getId());
                } else {
                    Log.e(TAG, "onResponse" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e(TAG, "onFailure" + t.getMessage());
            }
        });
    }
}