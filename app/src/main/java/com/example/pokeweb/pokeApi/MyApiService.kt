package com.example.pokeweb.pokeApi

import com.example.pokeweb.models.UserInfo
import com.example.pokeweb.models.UserInfoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApiService {
    @POST("confirmarUsuario")
    fun confirmUser(@Body userData: UserInfo): Call<UserInfoResponse>
    @POST("registrarUsuario")
    fun registerUser(@Body userData: UserInfo): Call<UserInfoResponse>
}