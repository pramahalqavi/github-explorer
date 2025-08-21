package com.example.explorer.data.repository

import android.util.Log
import com.example.explorer.data.api.GithubApi
import com.example.explorer.view.model.RepositoryUiModel
import com.example.explorer.view.model.SearchUiModel
import com.example.explorer.view.model.UserDetailUiModel
import com.example.explorer.view.model.UserUiModel

class GithubRepositoryImpl(private val githubApi: GithubApi) : GithubRepository {
    override suspend fun getUsers(since: Int?, perPage: Int): Result<List<UserUiModel>> {
        return try {
            val result = githubApi.getUsers(since, perPage)
            Result.success(result.map { UserUiModel.fromEntity(it) })
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun searchUsers(
        query: String,
        perPage: Int,
        page: Int
    ): Result<SearchUiModel> {
        return try {
            val result = githubApi.searchUsers(query, perPage, page)
            Result.success(SearchUiModel.fromEntity(result))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun getUserDetails(username: String): Result<UserDetailUiModel> {
        return try {
            val result = githubApi.getUserDetails(username)
            Result.success(UserDetailUiModel.fromEntity(result))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun getUserRepositories(
        username: String,
        perPage: Int,
        page: Int
    ): Result<List<RepositoryUiModel>> {
        return try {
            val result = githubApi.getUserRepositories(username, perPage, page)
            Result.success(result.map { RepositoryUiModel.fromEntity(it) })
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

}