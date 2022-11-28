package com.example.pokeweb.models;

import java.util.ArrayList;

public class Pokemon {
    //Variables
    private int id;
    private String name;
    private int weight;
    private int height;
    private ArrayList<Pokemon> types;
    private Sprites sprites;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public Sprites getSprites() {
        return sprites;
    }
    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }
    public ArrayList<Pokemon> getTypes() {
        return types;
    }
    public void setTypes(ArrayList<Pokemon> types) {
        this.types = types;
    }
}
class Sprites {
    String front_default;
    String front_shiny;
}


