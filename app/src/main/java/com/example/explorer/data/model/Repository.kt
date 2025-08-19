package com.example.explorer.data.model

import com.google.gson.annotations.SerializedName

data class Repository(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    @SerializedName("html_url") val htmlUrl: String? = null,
    val language: String? = null
)
