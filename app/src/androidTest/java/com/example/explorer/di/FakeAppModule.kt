package com.example.explorer.di

import com.example.explorer.data.repository.GithubRepository
import com.example.explorer.utils.SchedulerProvider
import com.example.explorer.utils.SchedulerProviderImpl
import com.example.explorer.repository.FakeGithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class FakeAppModule {
    @Provides
    @Singleton
    fun provideGithubRepository(): GithubRepository = FakeGithubRepository()

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProviderImpl()
}