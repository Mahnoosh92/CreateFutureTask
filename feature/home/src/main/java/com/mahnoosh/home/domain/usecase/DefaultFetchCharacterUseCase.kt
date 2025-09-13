package com.mahnoosh.home.domain.usecase

import com.mahnoosh.home.domain.repository.CharacterRepository
import javax.inject.Inject

internal class DefaultFetchCharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository) :
    FetchCharacterUseCase {
    override suspend operator fun invoke() = characterRepository.fetchCharacters()
}