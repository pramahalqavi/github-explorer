package com.example.explorer.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val login: String? = null,
    val id: Int? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null
)
