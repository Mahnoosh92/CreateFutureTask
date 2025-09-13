package com.mahnoosh.home.di

import com.mahnoosh.common.di.CommonModule
import com.mahnoosh.common.stringresolver.StringResolver
import com.mahnoosh.ui.FakeStringResolver
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CommonModule::class]
)
abstract class TestCommonModule {

    @Binds
    internal abstract fun bindStringResolver(
        fakeStringResolver: FakeStringResolver
    ): StringResolver
}