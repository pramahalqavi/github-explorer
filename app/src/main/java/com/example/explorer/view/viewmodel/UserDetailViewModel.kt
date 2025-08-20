package com.example.explorer.view.viewmodel

import androidx.lifecycle.LiveData
import com.example.explorer.view.model.UserDetailAdapterModel

interface UserDetailViewModel {
    val combinedDetailsLiveData: LiveData<List<UserDetailAdapterModel>>
    val isLoadingLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<Throwable?>

    fun fetchUserDetails(username: String)
    fun getUsername(): String
    fun fetchMoreRepos()
}