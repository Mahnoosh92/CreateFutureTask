package com.mahnoosh.home.data.datasource.remote

import com.mahnoosh.network.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class DefaultCharacterRemoteDatasourceTest {

    private val apiService = mockk<ApiService>()
    private lateinit var datasource: CharacterRemoteDatasource

    @Before
    fun setUp() {
        datasource = DefaultCharacterRemoteDatasource(apiService = apiService)
    }

    @Test
    fun `fetchCharacters calls apiService_fetchCharacters once`() = runTest {
        coEvery { apiService.fetchCharacters() } returns Response.success(emptyList())

        datasource.fetchCharacters()

        coVerify(exactly = 1) { apiService.fetchCharacters() }
    }
}