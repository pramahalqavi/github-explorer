package com.example.explorer.view.viewmodel

import com.example.explorer.InstantTaskExecutorExtension
import com.example.explorer.MockSchedulerProvider
import com.example.explorer.data.repository.GithubRepository
import com.example.explorer.utils.SchedulerProvider
import com.example.explorer.view.model.SearchUiModel
import com.example.explorer.view.model.UserUiModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class UserListViewModelTest {

    private lateinit var viewModel: UserListViewModel
    @RelaxedMockK
    private lateinit var githubRepository: GithubRepository
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var schedulerProvider: SchedulerProvider

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        schedulerProvider = MockSchedulerProvider(testDispatcher)
        viewModel = UserListViewModelImpl(githubRepository, schedulerProvider)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun test_fetchUsers_success_shouldReturnUserList() {
        val users = listOf(
            UserUiModel("user1", 1, "https://example.com/user1.png"),
        )
        coEvery { githubRepository.getUsers() } returns Result.success(users)

        viewModel.fetchUsers(isInitial = true)

        assert(viewModel.userListLiveData.value.orEmpty().isNotEmpty())
        assert(viewModel.userListLiveData.value == users)
        assert(viewModel.isLoadingLiveData.value == false)
        assert(viewModel.errorLiveData.value == null)
    }

    @Test
    fun test_fetchUsers_fail_shouldReturnThrowable() {
        coEvery { githubRepository.getUsers() } returns Result.failure(IllegalStateException())

        viewModel.fetchUsers(isInitial = true)

        assert(viewModel.userListLiveData.value.orEmpty().isEmpty())
        assert(viewModel.isLoadingLiveData.value == false)
        assert(viewModel.errorLiveData.value is IllegalStateException)
    }

    @Test
    fun test_searchUsers_success_shouldReturnUserList() = runTest {
        val searchResult = SearchUiModel(
            totalCount = 10,
            users = listOf(
                UserUiModel("user1", 1, "https://example.com/user1.png")
            )
        )
        val nexPage = SearchUiModel(
            totalCount = 10,
            users = listOf(
                UserUiModel("user2", 2, "https://example.com/user2.png")
            )
        )
        coEvery { githubRepository.searchUsers(query = any(), any(), any()) } returns Result.success(searchResult)

        viewModel.searchUsers("query")
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.userListLiveData.value.orEmpty().size == 1)
        assert(viewModel.userListLiveData.value == searchResult.users)
        assert(viewModel.isLoadingLiveData.value == false)
        assert(viewModel.errorLiveData.value == null)

        coEvery { githubRepository.searchUsers(query = any(), any(), any()) } returns Result.success(nexPage)
        viewModel.searchUsersNextPage()
        assert(viewModel.userListLiveData.value.orEmpty().size == 2)
        assert(viewModel.userListLiveData.value?.get(1)?.id == 2)
        assert(viewModel.isLoadingLiveData.value == false)
        assert(viewModel.errorLiveData.value == null)
    }

    @Test
    fun test_searchUsers_fail_shouldReturnThrowable() {
        coEvery { githubRepository.searchUsers(any()) } returns Result.failure(IllegalStateException())

        viewModel.searchUsers("query")

        assert(viewModel.userListLiveData.value.orEmpty().isEmpty())
        assert(viewModel.isLoadingLiveData.value == false)
        assert(viewModel.errorLiveData.value == null)
    }

    @Test
    fun test_retry_success_shouldReturnUserList() {
        val users = listOf(
            UserUiModel("user1", 1, "https://example.com/user1.png"),
        )
        coEvery { githubRepository.getUsers() } returns Result.success(users)

        viewModel.retry()

        assert(viewModel.userListLiveData.value.orEmpty().isNotEmpty())
        assert(viewModel.userListLiveData.value == users)
        assert(viewModel.isLoadingLiveData.value == false)
        assert(viewModel.errorLiveData.value == null)
    }
}