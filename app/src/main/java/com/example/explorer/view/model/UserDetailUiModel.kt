package com.example.explorer.view.model

import com.example.explorer.data.model.UserDetailEntity

data class UserDetailUiModel(
    val login: String,
    val id: Int,
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val email: String,
    val bio: String,
    val avatarUrl: String,
    val publicRepos: Int,
    val followers: Int,
    val following: Int
) {
    companion object {
        fun fromEntity(entity: UserDetailEntity): UserDetailUiModel {
            return UserDetailUiModel(
                login = entity.login.orEmpty(),
                id = entity.id ?: 0,
                name = entity.name.orEmpty(),
                company = entity.company.orEmpty(),
                blog = entity.blog.orEmpty(),
                location = entity.location.orEmpty(),
                email = entity.email.orEmpty(),
                bio = entity.bio.orEmpty(),
                avatarUrl = entity.avatarUrl.orEmpty(),
                publicRepos = entity.publicRepos ?: 0,
                followers = entity.followers ?: 0,
                following = entity.following ?: 0
            )
        }
    }
}
