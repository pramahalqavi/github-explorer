package com.example.explorer.repository

import com.example.explorer.data.repository.GithubRepository
import com.example.explorer.view.model.RepositoryUiModel
import com.example.explorer.view.model.SearchUiModel
import com.example.explorer.view.model.UserDetailUiModel
import com.example.explorer.view.model.UserUiModel
import javax.inject.Inject

class FakeGithubRepository @Inject constructor() : GithubRepository {

    companion object {
        var shouldFailUsers = false
        var shouldFailSearch = false
        var shouldFailUserDetails = false
        var shouldFailRepositories = false
    }

    var fakeUsers: List<UserUiModel> = listOf(
        UserUiModel(
            login = "alice",
            id = 8416,
            avatarUrl = "http://www.bing.com/search?q=reque"
        ), UserUiModel(login = "bob", id = 4445, avatarUrl = "https://duckduckgo.com/?q=causae")
    )
    var fakeSearchResult: SearchUiModel = SearchUiModel(totalCount = 1, listOf(
        UserUiModel(
            login = "alice",
            id = 8416,
            avatarUrl = "http://www.bing.com/search?q=reque"
        )
    ))
    var fakeUserDetails: UserDetailUiModel = UserDetailUiModel(
        login = "alice",
        id = 4391,
        name = "Lou Miranda",
        company = "feugiat",
        blog = "luptatum",
        location = "mollis",
        email = "diann.bowen@example.com",
        bio = "vix",
        avatarUrl = "https://search.yahoo.com/search?p=cetero",
        publicRepos = 2691,
        followers = 5958,
        following = 1259
    )
    var fakeRepositories: List<RepositoryUiModel> = listOf(
        RepositoryUiModel(
            id = 7668,
            name = "repo1",
            description = "sea",
            htmlUrl = "https://search.yahoo.com/search?p=viverra",
            language = "id"
        ),
        RepositoryUiModel(
            id = 6768,
            name = "repo2",
            description = "sea",
            htmlUrl = "https://search.yahoo.com/search?p=cetero",
            language = "en"
        )
    )

    override suspend fun getUsers(since: Int?, perPage: Int): Result<List<UserUiModel>> {
        return if (shouldFailUsers) {
            Result.failure(RuntimeException("Failed to fetch users"))
        } else {
            Result.success(fakeUsers)
        }
    }

    override suspend fun searchUsers(
        query: String,
        perPage: Int,
        page: Int
    ): Result<SearchUiModel> {
        return if (shouldFailSearch) {
            Result.failure(RuntimeException("Failed to search users"))
        } else {
            Result.success(fakeSearchResult)
        }
    }

    override suspend fun getUserDetails(username: String): Result<UserDetailUiModel> {
        return if (shouldFailUserDetails) {
            Result.failure(RuntimeException("Failed to fetch user details"))
        } else {
            Result.success(fakeUserDetails)
        }
    }

    override suspend fun getUserRepositories(
        username: String,
        perPage: Int,
        page: Int
    ): Result<List<RepositoryUiModel>> {
        return if (shouldFailRepositories) {
            Result.failure(RuntimeException("Failed to fetch repositories"))
        } else {
            Result.success(fakeRepositories)
        }
    }
}