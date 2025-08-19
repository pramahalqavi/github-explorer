package com.example.explorer.data.model

import com.google.gson.annotations.SerializedName

data class UserEntity(
    val login: String? = null,
    val id: Int? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null
)
