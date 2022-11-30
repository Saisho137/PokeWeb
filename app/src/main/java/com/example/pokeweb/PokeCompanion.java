package com.example.pokeweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.pokeweb.models.Companions;
import com.example.pokeweb.models.CompanionsResponse;
import com.example.pokeweb.models.Pokemon;
import com.example.pokeweb.pokeApi.MyApiService;
import com.example.pokeweb.pokeApi.PokeApiService;
import com.example.pokeweb.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokeCompanion extends AppCompatActivity {

    private Retrofit retrofit;
    private Retrofit retrofit2;

    private static final String TAG = "POKEDEX";

    private RecyclerView recyclerView;
    private PokemonListAdapter pokemonListAdapter;

    private ArrayList<Integer> companions;
    //private IdCompanionDTO idCompanionDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_companion);

        //EditText
        //EditText emailTb = findViewById(R.id.tbEmailR);//EditText variable

        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pokemonListAdapter = new PokemonListAdapter(this);
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        //Declarations
        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit2= new Retrofit.Builder()
                .baseUrl("https://b321-2800-e2-280-a76-2840-f00a-32b5-5052.ngrok.io/api/PokeWeb/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        companions = new ArrayList<>();

        //Send Companions Package
        String email = emailCompanion.contentEditText;
        String emailUser;
        Companions userId = new Companions( emailUser = email );

        //Render All
        getIdCompanion(userId);
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
    private void getIdCompanion(Companions userData) {
        MyApiService service = retrofit2.create(MyApiService.class);
        Call<CompanionsResponse> call = service.requestCompanion(userData);

        call.enqueue( new  Callback<CompanionsResponse>() {
            @Override
            public void onResponse(Call<CompanionsResponse> call, Response<CompanionsResponse> response) {
            ArrayList<Integer> confirm = response.body().getIdCompanion();
            if (confirm != null) {
                companions.addAll(confirm);
                Log.i("TEST SET ID","content Get Id Confirm: " + confirm);
                Log.i("TEST SET ID","Global Before Start Activity: " + companions);
            }  else  {
                Log.e("TEST ID","NOT RESPONSE: " + null);
                companions.add(25);
            }
                getData(companions);
            }
            @Override
            public void onFailure(Call<CompanionsResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure" + t.getMessage());
            }
        });
    }
}