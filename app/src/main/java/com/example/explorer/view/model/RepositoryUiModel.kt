package com.example.explorer.view.model

import com.example.explorer.data.model.Repository

data class RepositoryUiModel(
    val id: Int,
    val name: String,
    val description: String,
    val htmlUrl: String,
    val language: String
) : UserDetailAdapterModel {
    companion object {
        fun fromEntity(entity: Repository): RepositoryUiModel {
            return RepositoryUiModel(
                id = entity.id ?: 0,
                name = entity.name.orEmpty(),
                description = entity.description.orEmpty(),
                htmlUrl = entity.htmlUrl.orEmpty(),
                language = entity.language.orEmpty()
            )
        }
    }
}
