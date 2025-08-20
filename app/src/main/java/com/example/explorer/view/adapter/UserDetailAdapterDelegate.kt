package com.example.explorer.view.adapter

import com.bumptech.glide.Glide
import com.example.explorer.view.model.UserDetailUiModel
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.ItemUserDetailsBinding
import com.github.pramahalqavi.adapterdelegate.model.BindingDelegateItem
import com.github.pramahalqavi.adapterdelegate.model.viewholder.BindingDelegateViewHolder

class UserDetailAdapterDelegate : BindingDelegateItem<UserDetailUiModel, ItemUserDetailsBinding>(ItemUserDetailsBinding::inflate) {
    override fun onBind(item: UserDetailUiModel, holder: BindingDelegateViewHolder<ItemUserDetailsBinding>) {
        with(holder.binding) {
            Glide.with(ivAvatar.context).load(item.avatarUrl).circleCrop()
                .placeholder(R.drawable.bg_avatar).into(ivAvatar)
            tvUsername.text = item.login
            tvName.text = item.name
            tvFollowingValue.text = item.following.toString()
            tvFollowersValue.text = item.followers.toString()
            tvReposValue.text = item.publicRepos.toString()
        }
    }

    override fun isForViewType(item: Any, position: Int): Boolean = item is UserDetailUiModel
}