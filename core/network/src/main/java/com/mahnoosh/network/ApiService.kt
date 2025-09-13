package com.mahnoosh.network

import com.mahnoosh.network.model.CharacterDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("characters")
    suspend fun fetchCharacters(): Response<List<CharacterDTO>>
}