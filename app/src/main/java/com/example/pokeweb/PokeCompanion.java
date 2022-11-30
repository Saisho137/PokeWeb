package com.example.pokeweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.pokeweb.models.Companions;
import com.example.pokeweb.models.CompanionsResponse;
import com.example.pokeweb.models.Pokemon;
import com.example.pokeweb.pokeApi.MyApiService;
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

    private ArrayList<Integer> companions;
    private IdCompanionDTO idCompanionDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_companion);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pokemonListAdapter = new PokemonListAdapter(this);
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Declaration Object
        idCompanionDTO = new IdCompanionDTO(this);
        companions = new ArrayList<>();
        //debug
        Log.e("Response List ID 3","Content 2: " + idCompanionDTO.getIdList() );
        //Validation and default Pokemon (Pikachu = 25)
        if (idCompanionDTO.getIdList().isEmpty()){
            companions.add(25);
            getData(companions);
            Log.e("Response List ID 2","Content Fail: " + idCompanionDTO.getIdList() );
        }else{
            companions.addAll( idCompanionDTO.getIdList() );
            getData(companions);
            Log.i("Response List ID 1","Content Success: " + idCompanionDTO.getIdList() );
        }
        //

    }

    private void getData(ArrayList<Integer> comp) {
        for (int i = 0 ; i < comp.size(); i++){
            PokeApiService service = retrofit.create(PokeApiService.class);
            Call<Pokemon> PokemonCall = service.getPokemonList((Integer) comp.get(i));
            PokemonCall.enqueue(new Callback<Pokemon>() {
                @Override
                public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                    if (response.isSuccessful()) {
                        Pokemon pokemonResponse = response.body();
                        ArrayList<Pokemon> pokemonList = new ArrayList<>();
                        pokemonList.add(pokemonResponse);
                        pokemonListAdapter.addPokemonList(pokemonList);
                        //Log.i("Response","Content: " + pokemonResponse.getName() +" id: " + pokemonResponse.getId());
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
}