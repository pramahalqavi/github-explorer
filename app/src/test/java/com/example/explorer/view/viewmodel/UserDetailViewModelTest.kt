package com.example.explorer.view.viewmodel

import com.example.explorer.InstantTaskExecutorExtension
import com.example.explorer.MockSchedulerProvider
import com.example.explorer.data.repository.GithubRepository
import com.example.explorer.utils.SchedulerProvider
import com.example.explorer.view.model.RepositoryUiModel
import com.example.explorer.view.model.UserDetailAdapterModel
import com.example.explorer.view.model.UserDetailUiModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class UserDetailViewModelTest {

    private lateinit var viewModel: UserDetailViewModel
    @RelaxedMockK
    private lateinit var githubRepository: GithubRepository
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var schedulerProvider: SchedulerProvider

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        schedulerProvider = MockSchedulerProvider(testDispatcher)
        viewModel = UserDetailViewModelImpl(githubRepository, schedulerProvider)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun test_fetchUserDetails_success_shouldReturnDetailsAndRepos() {
        val detail = UserDetailUiModel(
            login = "fermentum",
            id = 5173,
            name = "Joesph Fernandez",
            company = "habitant",
            blog = "utamur",
            location = "repudiandae",
            email = "eduardo.campbell@example.com",
            bio = "theophrastus",
            avatarUrl = "http://www.bing.com/search?q=efficiantur",
            publicRepos = 3302,
            followers = 1769,
            following = 2388
        )
        val repos = listOf(
            RepositoryUiModel(
                id = 8067,
                name = "Madge Lyons",
                description = "graecis",
                htmlUrl = "http://www.bing.com/search?q=sententiae",
                language = "ubique"
            )
        )
        coEvery { githubRepository.getUserDetails(any()) } returns Result.success(detail)
        coEvery { githubRepository.getUserRepositories(any(), any(), any()) } returns Result.success(repos)

        val combinedDetails = arrayListOf<List<UserDetailAdapterModel>>()
        viewModel.combinedDetailsLiveData.observeForever {
            combinedDetails.add(it)
        }

        viewModel.fetchUserDetails("username")

        val result = combinedDetails.last()
        assert(result[0] as UserDetailUiModel == detail)
        assert(result[1] as RepositoryUiModel == repos[0])
        assert(viewModel.isLoadingLiveData.value == false)
        assert(viewModel.errorLiveData.value == null)
        assert(viewModel.getUsername() == "username")
    }

    @Test
    fun test_fetchUserDetails_userDetailFail_shouldReturnThrowable() {
        val repos = listOf(
            RepositoryUiModel(
                id = 8067,
                name = "Madge Lyons",
                description = "graecis",
                htmlUrl = "http://www.bing.com/search?q=sententiae",
                language = "ubique"
            )
        )
        coEvery { githubRepository.getUserDetails(any()) } returns Result.failure(IllegalStateException())
        coEvery { githubRepository.getUserRepositories(any(), any(), any()) } returns Result.success(repos)

        viewModel.fetchUserDetails("username")

        assert(viewModel.combinedDetailsLiveData.value.orEmpty().isEmpty())
        assert(viewModel.isLoadingLiveData.value == false)
        assert(viewModel.errorLiveData.value is IllegalStateException)
    }


}