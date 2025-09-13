package com.mahnoosh.home.domain.usecase

import com.mahnoosh.home.domain.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class DefaultFetchCharacterUseCaseTest {
    private val repository = mockk<CharacterRepository>()
    private lateinit var useCase: FetchCharacterUseCase

    @Before
    fun setUp() {
        useCase = DefaultFetchCharacterUseCase(characterRepository = repository)
    }

    @Test
    fun `invoke calls fetchCharacters on the repository`() = runTest {
        coEvery { repository.fetchCharacters() } returns Result.success(emptyList())

        useCase()

        coVerify(exactly = 1) { repository.fetchCharacters() }
    }
}