package com.example.explorer.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count") val totalCount: Int? = null,
    @SerializedName("incomplete_results") val incompleteResults: Boolean? = null,
    val items: List<UserEntity>? = null
)
