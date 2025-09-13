package com.mahnoosh.home.data.datasource.remote

import com.mahnoosh.network.model.CharacterDTO
import retrofit2.Response

internal interface CharacterRemoteDatasource {
    /**
     * Returns list of characters
     */
    suspend fun fetchCharacters(): Response<List<CharacterDTO>>
}