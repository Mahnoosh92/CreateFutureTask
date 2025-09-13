package com.mahnoosh.home.domain.usecase

import com.mahnoosh.home.domain.model.Character

internal interface FetchCharacterUseCase {
    suspend operator fun invoke(): Result<List<Character>>
}