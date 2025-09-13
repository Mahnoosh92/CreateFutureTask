package com.mahnoosh.home.domain.repository

import com.mahnoosh.home.domain.model.Character

internal interface CharacterRepository {

    /**
     * Returns list of characters
     */
    suspend fun fetchCharacters(): Result<List<Character>>
}