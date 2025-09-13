package com.mahnoosh.home.data.repository

import com.mahnoosh.common.R
import com.mahnoosh.common.error.RepositoryError
import com.mahnoosh.common.stringresolver.StringResolver
import com.mahnoosh.home.data.datasource.remote.CharacterRemoteDatasource
import com.mahnoosh.home.data.mapper.toCharacter
import com.mahnoosh.home.domain.model.Character
import com.mahnoosh.home.domain.repository.CharacterRepository
import com.mahnoosh.network.model.CharacterDTO
import com.mahnoosh.threading.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultCharacterRepository @Inject constructor(
    private val datasource: CharacterRemoteDatasource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val stringResolver: StringResolver
) :
    CharacterRepository {
    override suspend fun fetchCharacters(): Result<List<Character>> =
        withContext(ioDispatcher) {
            try {
                datasource.fetchCharacters().let { response ->
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            Result.success(body.map(CharacterDTO::toCharacter))
                        } ?: Result.failure(
                            RepositoryError.NoDataError(
                                stringResolver.findString(
                                    R.string.error_response_body_is_null
                                )
                            )
                        )
                    } else {
                        Result.failure(
                            RepositoryError.NetworkError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                Result.failure(RepositoryError.UnknownError(e))
            }
        }
}