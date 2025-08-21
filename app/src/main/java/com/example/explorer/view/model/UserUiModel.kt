package com.example.explorer.view.model

import com.example.explorer.data.model.User

data class UserUiModel(
    val login: String,
    val id: Int,
    val avatarUrl: String
) {
    companion object {
        fun fromEntity(entity: User): UserUiModel {
            return UserUiModel(
                login = entity.login.orEmpty(),
                id = entity.id ?: 0,
                avatarUrl = entity.avatarUrl.orEmpty()
            )
        }
    }
}