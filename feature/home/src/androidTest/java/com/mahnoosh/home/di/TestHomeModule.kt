package com.mahnoosh.home.di

import com.mahnoosh.home.data.di.HomeModule
import com.mahnoosh.home.domain.usecase.FakeCharacterUseCase
import com.mahnoosh.home.domain.usecase.FetchCharacterUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [HomeModule::class]
)
abstract class TestHomeModule {

    @Binds
    internal abstract fun bindFetchCharacterUseCase(
        fakeCharacterUseCase: FakeCharacterUseCase
    ): FetchCharacterUseCase
}