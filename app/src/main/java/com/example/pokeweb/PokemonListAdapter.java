package com.example.pokeweb;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokeweb.models.Pokemon;

import java.util.ArrayList;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataset;

    public PokemonListAdapter() {
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        Log.i("Busqueda", "position is " + position);
        String firsCapitalLetter = p.getName().toUpperCase().charAt(0) + p.getName().substring(1).toLowerCase();
        holder.textViewPokedex.setText( firsCapitalLetter );//Setear el nombre de cada pokemon
        holder.textViewId.setText("#"+p.getId());
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addPokemonList(ArrayList<Pokemon> pokemonList) {
        dataset.addAll(pokemonList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewPokedex;
        private TextView textViewId;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewPokedex = itemView.findViewById(R.id.textViewPokedex);
            textViewId = itemView.findViewById(R.id.textViewId);
        }

    }

}
