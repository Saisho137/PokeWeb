package com.example.pokeweb;

import android.content.Context;

import com.example.pokeweb.models.Pokemon;

import java.util.ArrayList;

public class IdCompanionDTO {

    public ArrayList<Integer> idList;
    public Context context;

    public IdCompanionDTO(Context context) {
        this.context = context;
        idList = new ArrayList<>();
    }

    public ArrayList<Integer> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<Integer> idList) {
        this.idList = idList;
    }

}
