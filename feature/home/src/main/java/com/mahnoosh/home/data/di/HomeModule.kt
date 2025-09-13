package com.mahnoosh.home.data.di

import com.mahnoosh.home.data.datasource.remote.CharacterRemoteDatasource
import com.mahnoosh.home.data.datasource.remote.DefaultCharacterRemoteDatasource
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
}