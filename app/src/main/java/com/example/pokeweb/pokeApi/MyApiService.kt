package com.example.pokeweb.pokeApi

import com.example.pokeweb.models.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApiService {
    @POST("confirmarUsuario")
    fun confirmUser(@Body userData: UserInfo): Call<UserInfo>
}