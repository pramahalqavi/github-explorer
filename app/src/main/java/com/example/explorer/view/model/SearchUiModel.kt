package com.example.explorer.view.model

import com.example.explorer.data.model.SearchResponse

data class SearchUiModel(
    val totalCount: Int,
    val isEndOfPage: Boolean = false,
    val users: List<UserUiModel>
) {
    companion object {
        fun fromEntity(entity: SearchResponse): SearchUiModel {
            return SearchUiModel(
                totalCount = entity.totalCount ?: 0,
                isEndOfPage = entity.incompleteResults?.not() ?: true,
                users = entity.items.orEmpty().map { UserUiModel.fromEntity(it) }
            )
        }
    }
}
