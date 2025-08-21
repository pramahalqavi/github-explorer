package com.example.explorer.data.repository

import com.example.explorer.data.api.GithubApi
import com.example.explorer.data.model.Repository
import com.example.explorer.data.model.SearchResponse
import com.example.explorer.data.model.UserDetail
import com.example.explorer.data.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.lang.IllegalStateException

class GithubRepositoryTest {
    private lateinit var repository: GithubRepository
    @RelaxedMockK
    private lateinit var githubApi: GithubApi


    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        repository = GithubRepositoryImpl(githubApi)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun test_getUsers_shouldReturnExpectedResult(isSuccess: Boolean) {
        if (isSuccess) {
            coEvery { githubApi.getUsers(any(), any()) } returns listOf(
                User("testUser1", 1, "https://avatar.url/1"),
                User("testUser2", 2, "https://avatar.url/2")
            )
        } else {
            coEvery { githubApi.getUsers(any(), any()) } throws IllegalStateException()
        }

        runTest {
            val result = repository.getUsers(since = null, perPage = 30)

            if (isSuccess) {
                assert(result.isSuccess)
                val users = result.getOrNull()
                assert(users != null && users.size == 2)
                assert(users?.get(0)?.login == "testUser1")
                assert(users?.get(1)?.login == "testUser2")
            } else {
                assert(result.isFailure)
                assert(result.exceptionOrNull() is IllegalStateException)
            }
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun test_searchUsers_shouldReturnExpectedResult(isSuccess: Boolean) {
        if (isSuccess) {
            coEvery { githubApi.searchUsers(any(), any(), any()) } returns SearchResponse(
                totalCount = 2,
                incompleteResults = false,
                items = listOf(
                    User("testUser1", 1, "https://avatar.url/1"),
                    User("testUser2", 2, "https://avatar.url/2")
                )
            )
        } else {
            coEvery { githubApi.searchUsers(any(), any(), any()) } throws IllegalStateException()
        }

        runTest {
            val result = repository.searchUsers(query = "test", perPage = 30, page = 1)

            if (isSuccess) {
                assert(result.isSuccess)
                val searchRes = result.getOrNull()
                assert(searchRes != null && searchRes.totalCount == 2)
                assert(searchRes?.users?.get(0)?.login == "testUser1")
                assert(searchRes?.users?.get(1)?.login == "testUser2")
            } else {
                assert(result.isFailure)
                assert(result.exceptionOrNull() is IllegalStateException)
            }
        }
    }
    
    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun test_getUserDetails_shouldReturnExpectedResult(isSuccess: Boolean) {
        if (isSuccess) {
            coEvery { githubApi.getUserDetails(any()) } returns UserDetail(
                login = "testuser",
                id = 1,
                name = "Test User",
                company = "Company",
                blog = "Blog",
                location = "Location",
                email = "email@example.com",
                bio = "bio",
                avatarUrl = "avatarUrl",
                publicRepos = 3,
                followers = 1,
                following = 2
            )
        } else {
            coEvery { githubApi.getUserDetails(any()) } throws IllegalStateException()
        }

        runTest {
            val result = repository.getUserDetails("testuser")

            if (isSuccess) {
                assert(result.isSuccess)
                val detail = result.getOrNull()
                assert(detail?.login == "testuser")
                assert(detail?.id == 1)
                assert(detail?.name == "Test User")
            } else {
                assert(result.isFailure)
                assert(result.exceptionOrNull() is IllegalStateException)
            }
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun test_getUserRepos_shouldReturnExpectedResult(isSuccess: Boolean) {
        if (isSuccess) {
            coEvery { githubApi.getUserRepositories(any(), any(), any()) } returns listOf(
                Repository(
                    id = 1,
                    name = "testuser-repo1",
                    description = "desc1",
                    htmlUrl = "html1",
                    language = "id"
                ),
                Repository(
                    id = 2,
                    name = "testuser-repo2",
                    description = "desc2",
                    htmlUrl = "html2",
                    language = "en"
                )
            )
        } else {
            coEvery { githubApi.getUserRepositories(any(), any(), any()) } throws IllegalStateException()
        }

        runTest {
            val result = repository.getUserRepositories("testuser", 30, 1)

            if (isSuccess) {
                assert(result.isSuccess)
                val repos = result.getOrNull()
                assert(repos != null && repos.size == 2)
                assert(repos?.get(0)?.id == 1)
                assert(repos?.get(1)?.id == 2)
            } else {
                assert(result.isFailure)
                assert(result.exceptionOrNull() is IllegalStateException)
            }
        }
    }
}