package com.example.pokeweb.models;

import java.util.ArrayList;

public class PokemonResponse {

    private ArrayList<Pokemon> types;

    public ArrayList<Pokemon> getResults() {
        return types;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.types = types;
    }
}
