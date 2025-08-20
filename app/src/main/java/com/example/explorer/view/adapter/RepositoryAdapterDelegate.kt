package com.example.explorer.view.adapter

import com.example.explorer.view.model.RepositoryUiModel
import com.example.githubexplorer.databinding.ItemRepoListBinding
import com.github.pramahalqavi.adapterdelegate.model.BindingDelegateItem
import com.github.pramahalqavi.adapterdelegate.model.viewholder.BindingDelegateViewHolder

class RepositoryAdapterDelegate : BindingDelegateItem<RepositoryUiModel, ItemRepoListBinding>(ItemRepoListBinding::inflate) {
    override fun onBind(item: RepositoryUiModel, holder: BindingDelegateViewHolder<ItemRepoListBinding>) {
        with(holder.binding) {
            tvName.text = item.name
            tvDescription.text = item.description
        }
    }

    override fun isForViewType(item: Any, position: Int): Boolean = item is RepositoryUiModel
}