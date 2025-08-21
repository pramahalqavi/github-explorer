package com.example.explorer.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.explorer.view.model.UserUiModel

class UserListDiffCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem is UserUiModel && newItem is UserUiModel && oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem is UserUiModel && newItem is UserUiModel && oldItem == newItem
    }
}