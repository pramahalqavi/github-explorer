package com.example.explorer.view.viewmodel

import androidx.lifecycle.LiveData
import com.example.explorer.view.model.UserUiModel

interface UserListViewModel {
    val userListLiveData: LiveData<List<UserUiModel>>
    val isLoadingLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<Throwable?>

    fun fetchUsers(isInitial: Boolean)
    fun searchUsers(query: String = "")
    fun searchUsersNextPage()
    fun retry()
}