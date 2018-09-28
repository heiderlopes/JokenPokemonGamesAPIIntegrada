package com.example.logonpflocal.jokenpokemon.api

import com.example.logonpflocal.jokenpokemon.model.Pontuacao
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JokenpokemonAPI {

    @GET("/jokenpokemon/pontuacao")
    fun buscarPontuacao(): Call<List<Pontuacao>>

    @POST("/jokenpokemon/pontuacao")
    fun enviarPontos(@Body pontuacao: Pontuacao): Call<Void>

}