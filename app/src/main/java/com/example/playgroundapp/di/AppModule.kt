package com.example.playgroundapp.di

import com.example.playgroundapp.utils.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideNetworkClient(): NetworkClient {
        return NetworkClient()
    }
}