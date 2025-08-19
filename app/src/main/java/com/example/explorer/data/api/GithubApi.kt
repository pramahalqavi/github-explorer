package com.example.explorer.data.api

import com.example.explorer.data.model.Repository
import com.example.explorer.data.model.SearchResponse
import com.example.explorer.data.model.UserDetailEntity
import com.example.explorer.data.model.UserEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int? = null,
        @Query("per_page") perPage: Int = 30
    ): List<UserEntity>

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 1
    ): SearchResponse

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): UserDetailEntity

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String,
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 1
    ): List<Repository>
}