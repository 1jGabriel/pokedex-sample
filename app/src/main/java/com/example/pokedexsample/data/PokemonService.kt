package com.example.pokedexsample.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonListResponse>
}

