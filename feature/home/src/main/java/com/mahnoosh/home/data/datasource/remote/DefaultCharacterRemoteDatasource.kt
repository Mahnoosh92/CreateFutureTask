package com.mahnoosh.home.data.datasource.remote

import com.mahnoosh.network.ApiService
import com.mahnoosh.network.model.CharacterDTO
import retrofit2.Response
import javax.inject.Inject

internal class DefaultCharacterRemoteDatasource @Inject constructor(private val apiService: ApiService) :
    CharacterRemoteDatasource {
    override suspend fun fetchCharacters(): Response<List<CharacterDTO>> =
        apiService.fetchCharacters()
}