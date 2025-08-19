package com.example.explorer.view.adapter

import com.bumptech.glide.Glide
import com.example.explorer.view.model.UserUiModel
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.ItemUserListBinding
import com.github.pramahalqavi.adapterdelegate.model.BindingDelegateItem
import com.github.pramahalqavi.adapterdelegate.model.viewholder.BindingDelegateViewHolder

class UserListAdapterDelegate(private val onClick: (username: String) -> Unit) : BindingDelegateItem<UserUiModel, ItemUserListBinding>(ItemUserListBinding::inflate) {
    override fun onBind(item: UserUiModel, holder: BindingDelegateViewHolder<ItemUserListBinding>) {
        with(holder.binding) {
            tvUsername.text = item.login
            Glide.with(ivAvatar.context).load(item.avatarUrl).circleCrop()
                .placeholder(R.drawable.bg_avatar).into(ivAvatar)
            root.setOnClickListener { onClick(item.login) }
        }
    }

    override fun isForViewType(item: Any, position: Int): Boolean = item is UserUiModel
}