package com.mahnoosh.common.di

import android.content.Context
import com.mahnoosh.common.stringresolver.StringResolver
import com.mahnoosh.common.utils.ConnectivityManagerNetworkMonitor
import com.mahnoosh.common.utils.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModule {
    @Binds
    internal abstract fun bindNetworkMonitor(
        connectivityManagerNetworkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor

    companion object{
        @Provides
        @Singleton
        fun provideStringResolver(@ApplicationContext context: Context): StringResolver =
            StringResolver(context::getString)
    }
}