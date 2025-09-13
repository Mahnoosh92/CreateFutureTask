package com.mahnoosh.home.data.repository

import com.google.common.truth.Truth.assertThat
import com.mahnoosh.common.error.RepositoryError
import com.mahnoosh.common.stringresolver.StringResolver
import com.mahnoosh.home.data.datasource.remote.CharacterRemoteDatasource
import com.mahnoosh.home.domain.repository.CharacterRepository
import com.mahnoosh.network.model.CharacterDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

internal class DefaultCharacterRepositoryTest {

    private val datasource = mockk<CharacterRemoteDatasource>()
    private val dispatcher = StandardTestDispatcher()
    private val stringResolver = mockk<StringResolver>()
    private lateinit var repository: CharacterRepository

    @Before
    fun setUp() {
        repository = DefaultCharacterRepository(
            datasource = datasource,
            ioDispatcher = dispatcher,
            stringResolver = stringResolver
        )
    }

    @Test
    fun `fetchCharacters returns NetworkError on unsuccessful HTTP response`() =
        runTest(dispatcher.scheduler) {
            val errorCode = 404
            val errorBody = "{\"error\": \"Not Found\"}"
            val errorResponse = Response.error<List<CharacterDTO>>(
                errorCode,
                errorBody.toResponseBody("application/json".toMediaTypeOrNull())
            )
            coEvery { datasource.fetchCharacters() } returns errorResponse

            val result = repository.fetchCharacters()

            assertThat(result.isFailure).isTrue()
            val failure = result.exceptionOrNull()
            assertThat(failure).isInstanceOf(RepositoryError.NetworkError::class.java)
            val networkError = failure as RepositoryError.NetworkError
            assertThat(networkError.code).isEqualTo(errorCode)
            assertThat(networkError.message).isEqualTo(errorBody)
        }

    @Test
    fun `fetchCharacters returns NoDataError when response body is null`() =
        runTest(dispatcher.scheduler) {
            val expectedErrorMessage = "Response body is null"
            coEvery { datasource.fetchCharacters() } returns Response.success(null)
            coEvery { stringResolver.findString(any()) } returns expectedErrorMessage

            val result = repository.fetchCharacters()

            assertThat(result.isFailure).isTrue()
            val failure = result.exceptionOrNull()
            assertThat(failure).isInstanceOf(RepositoryError.NoDataError::class.java)
            val noDataError = failure as RepositoryError.NoDataError
            assertThat(noDataError.message).isEqualTo(expectedErrorMessage)
        }

    @Test
    fun `fetchCharacters returns UnknownError on unexpected exception`() =
        runTest(dispatcher.scheduler) {
            val expectedErrorMessage = "Unexpected error during network call"
            val exception = Exception(expectedErrorMessage)
            coEvery { datasource.fetchCharacters() } throws exception

            val result = repository.fetchCharacters()

            assertThat(result.isFailure).isTrue()
            val failure = result.exceptionOrNull()
            assertThat(failure).isInstanceOf(RepositoryError.UnknownError::class.java)
            val unknownError = failure as RepositoryError.UnknownError
            assertThat(unknownError.cause?.message).isEqualTo(expectedErrorMessage)
        }
}