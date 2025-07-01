package com.example.rickandmorty

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("status") status: String? = null,
        @Query("species") species: String? = null
    ): Response<RickAndMortyResponse>
}
