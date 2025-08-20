package com.example.explorer.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explorer.data.repository.GithubRepository
import com.example.explorer.utils.SchedulerProvider
import com.example.explorer.view.model.UserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModelImpl @Inject constructor(
    private val githubRepository: GithubRepository,
    private val schedulerProvider: SchedulerProvider
) : UserListViewModel, ViewModel() {

    companion object {
        private const val DEBOUNCE_TIME = 1000L
        private const val MIN_QUERY_LENGTH = 3
    }

    private val _userListLiveData: MutableLiveData<List<UserUiModel>> = MutableLiveData()
    override val userListLiveData: LiveData<List<UserUiModel>> = _userListLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _errorLiveData: MutableLiveData<Throwable?> = MutableLiveData(null)
    override val errorLiveData: LiveData<Throwable?> = _errorLiveData

    private var query = ""
    private var currentPage: Int = 1
    private var isEndOfPage = false
    private var isLoadingPagination = false

    private var fetchJob: Job? = null

    override fun fetchUsers(isInitial: Boolean) {
        if (isInitial) {
            _isLoadingLiveData.postValue(true)
        }
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(schedulerProvider.io()) {
            githubRepository.getUsers().onSuccess { userList ->
                _isLoadingLiveData.postValue(false)
                _errorLiveData.postValue(null)
                _userListLiveData.postValue(userList)
            }.onFailure { e ->
                _isLoadingLiveData.postValue(false)
                _errorLiveData.postValue(e)
            }
        }
    }

    override fun searchUsers(query: String) {
        if (query.isEmpty()) {
            this.query = query
            fetchUsers(true)
            return
        } else if (!isEligibleToSearch(query)) return
        this.query = query
        searchUsersInternal(query, page = 1)
    }

    override fun searchUsersNextPage() {
        if (!isEligibleToSearch(query) || isEndOfPage || isLoadingPagination) return
        isLoadingPagination = true
        searchUsersInternal(query, currentPage + 1)
    }

    private fun searchUsersInternal(query: String, page: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(schedulerProvider.io()) {
            if (page == 1) {
                delay(DEBOUNCE_TIME)
                _isLoadingLiveData.postValue(true)
            }
            githubRepository.searchUsers(query, page = page).onSuccess { searchResult ->
                _isLoadingLiveData.postValue(false)
                _errorLiveData.postValue(null)
                var currentSize = searchResult.users.size
                if (page == 1) {
                    _userListLiveData.postValue(searchResult.users)
                } else {
                    currentSize += _userListLiveData.value.orEmpty().size
                    val users = ArrayList(_userListLiveData.value.orEmpty())
                    users.addAll(searchResult.users)
                    _userListLiveData.postValue(users)
                }
                isEndOfPage = searchResult.totalCount == currentSize
                currentPage = page
                isLoadingPagination = false
            }.onFailure { e ->
                _isLoadingLiveData.postValue(false)
                if (page == 1 && e !is CancellationException) {
                    _errorLiveData.postValue(e)
                }
                isLoadingPagination = false
            }
        }
    }

    override fun retry() {
        searchUsers(query)
    }

    private fun isEligibleToSearch(query: String): Boolean {
        return query.length >= MIN_QUERY_LENGTH
    }
}