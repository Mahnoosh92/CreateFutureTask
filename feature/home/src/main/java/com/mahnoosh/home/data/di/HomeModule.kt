package com.mahnoosh.home.data.di

import com.mahnoosh.home.data.datasource.remote.CharacterRemoteDatasource
import com.mahnoosh.home.data.datasource.remote.DefaultCharacterRemoteDatasource
import com.mahnoosh.home.data.repository.DefaultCharacterRepository
import com.mahnoosh.home.domain.repository.CharacterRepository
import com.mahnoosh.home.domain.usecase.DefaultFetchCharacterUseCase
import com.mahnoosh.home.domain.usecase.FetchCharacterUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    @Binds
    internal abstract fun bindCharacterRemoteDatasource(
        defaultCharacterRemoteDatasource: DefaultCharacterRemoteDatasource
    ): CharacterRemoteDatasource

    @Binds
    internal abstract fun bindCharacterRepository(
        defaultCharacterRepository: DefaultCharacterRepository
    ): CharacterRepository

    @Binds
    internal abstract fun bindFetchCharacterUseCase(
        defaultFetchCharacterUseCase: DefaultFetchCharacterUseCase
    ): FetchCharacterUseCase
}