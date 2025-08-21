package com.example.explorer.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.explorer.view.model.RepositoryUiModel
import com.example.explorer.view.model.UserDetailUiModel

class UserDetailDiffCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is UserDetailUiModel && newItem is UserDetailUiModel) {
            oldItem.id == newItem.id
        } else if (oldItem is RepositoryUiModel && newItem is RepositoryUiModel) {
            oldItem.id == newItem.id
        } else false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is UserDetailUiModel && newItem is UserDetailUiModel) {
            oldItem == newItem
        } else if (oldItem is RepositoryUiModel && newItem is RepositoryUiModel) {
            oldItem == newItem
        } else false
    }
}