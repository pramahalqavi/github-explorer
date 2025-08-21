package com.example.explorer.data.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    val login: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val bio: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("public_repos") val publicRepos: Int? = null,
    @SerializedName("followers") val followers: Int? = null,
    @SerializedName("following") val following: Int? = null
)
