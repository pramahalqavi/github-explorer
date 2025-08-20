package com.example.explorer.data.repository

import com.example.explorer.view.model.RepositoryUiModel
import com.example.explorer.view.model.SearchUiModel
import com.example.explorer.view.model.UserDetailUiModel
import com.example.explorer.view.model.UserUiModel

interface GithubRepository {
    suspend fun getUsers(since: Int? = null, perPage: Int = 50): Result<List<UserUiModel>>
    suspend fun searchUsers(query: String, perPage: Int = 30, page: Int = 1): Result<SearchUiModel>
    suspend fun getUserDetails(username: String): Result<UserDetailUiModel>
    suspend fun getUserRepositories(username: String, perPage: Int = 30, page: Int = 1): Result<List<RepositoryUiModel>>
}