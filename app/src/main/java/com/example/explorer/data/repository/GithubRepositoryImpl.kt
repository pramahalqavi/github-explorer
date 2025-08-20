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
            Log.d("GITHUB_REPO", "Fetched users: $result")
            Result.success(result.map { UserUiModel.fromEntity(it) })
        } catch (e: Throwable) {
            Log.d("GITHUB_REPO", "Fetched users error: $e")
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
            Log.d("GITHUB_REPO", "Fetched search $query page $page: $result")
            Result.success(SearchUiModel.fromEntity(result))
        } catch (e: Throwable) {
            Log.d("GITHUB_REPO", "Fetched search $query error: $e")
            Result.failure(e)
        }
    }

    override suspend fun getUserDetails(username: String): Result<UserDetailUiModel> {
        return try {
            val result = githubApi.getUserDetails(username)
            Log.d("GITHUB_REPO", "Fetched user details $username: $result")
            Result.success(UserDetailUiModel.fromEntity(result))
        } catch (e: Throwable) {
            Log.d("GITHUB_REPO", "Fetched user details error: $e")
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
            Log.d("GITHUB_REPO", "Fetched user repositories for $username page $page: $result")
            Result.success(result.map { RepositoryUiModel.fromEntity(it) })
        } catch (e: Throwable) {
            Log.d("GITHUB_REPO", "Fetched user repositories error: $e")
            Result.failure(e)
        }
    }

}