package com.example.explorer.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explorer.data.repository.GithubRepository
import com.example.explorer.utils.SchedulerProvider
import com.example.explorer.view.model.RepositoryUiModel
import com.example.explorer.view.model.UserDetailAdapterModel
import com.example.explorer.view.model.UserDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModelImpl @Inject constructor(
    private val githubRepository: GithubRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), UserDetailViewModel {
    private val userDetailLiveData = MutableLiveData<UserDetailUiModel>()
    private val repositoryLiveData = MutableLiveData<List<RepositoryUiModel>>()

    private val _combinedDetailsLiveData = MediatorLiveData<List<UserDetailAdapterModel>>().apply {
        addSource(userDetailLiveData) { detail ->
            val models = ArrayList<UserDetailAdapterModel>().apply { add(detail) }
            repositoryLiveData.value?.let { repos -> models.addAll(repos) }
            postValue(models)
        }
        addSource(repositoryLiveData) { repos ->
            userDetailLiveData.value?.let { detail ->
                val models = ArrayList<UserDetailAdapterModel>().apply { add(detail) }
                models.addAll(repos)
                postValue(models)
            }
        }
    }
    override val combinedDetailsLiveData: LiveData<List<UserDetailAdapterModel>> = _combinedDetailsLiveData
    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData
    private val _errorLiveData: MutableLiveData<Throwable?> = MutableLiveData(null)
    override val errorLiveData: LiveData<Throwable?> = _errorLiveData

    private var username: String = ""
    private var currentPage = 1
    private var isEndOfPage = false
    private var isLoadingPagination = false

    override fun getUsername(): String = username

    override fun fetchUserDetails(username: String) {
        this.username = username
        viewModelScope.launch(schedulerProvider.io()) {
            _isLoadingLiveData.postValue(true)
            githubRepository.getUserDetails(username).onSuccess {
                _isLoadingLiveData.postValue(false)
                userDetailLiveData.postValue(it)
            }.onFailure { e ->
                _isLoadingLiveData.postValue(false)
                if (e !is CancellationException) _errorLiveData.postValue(e)
            }
        }
        if (repositoryLiveData.value.isNullOrEmpty()) fetchRepos(username, 1)
    }

    override fun fetchMoreRepos() {
        if (repositoryLiveData.value.isNullOrEmpty() || isEndOfPage || isLoadingPagination) return
        isLoadingPagination = true
        fetchRepos(username, page = currentPage + 1)
    }

    private fun fetchRepos(username: String, page: Int, perPage: Int = 30) {
        viewModelScope.launch(schedulerProvider.io()) {
            githubRepository.getUserRepositories(username, perPage = perPage, page = page).onSuccess { repos ->
                ArrayList<RepositoryUiModel>().apply {
                    addAll(repositoryLiveData.value.orEmpty())
                    addAll(repos)
                }.also { updatedRepos ->
                    repositoryLiveData.postValue(updatedRepos)
                }
                currentPage = page
                isLoadingPagination = false
                isEndOfPage = repos.size < perPage
            }.onFailure {
                isLoadingPagination = false
            }
        }
    }
}