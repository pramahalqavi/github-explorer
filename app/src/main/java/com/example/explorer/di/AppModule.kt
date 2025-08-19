package com.example.explorer.di

import com.example.explorer.data.api.GithubApi
import com.example.explorer.data.repository.GithubRepository
import com.example.explorer.data.repository.GithubRepositoryImpl
import com.example.explorer.utils.SchedulerProvider
import com.example.explorer.utils.SchedulerProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    companion object {
        private const val GITHUB_API_BASE_URL = "https://api.github.com/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GITHUB_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGitHubApiService(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGithubRepository(githubApi: GithubApi): GithubRepository {
        return GithubRepositoryImpl(githubApi)
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider {
        return SchedulerProviderImpl()
    }
}