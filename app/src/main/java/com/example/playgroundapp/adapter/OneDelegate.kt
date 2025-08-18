package com.example.playgroundapp.adapter

import com.example.playgroundapp.databinding.ItemOneBinding
import com.example.playgroundapp.model.OneModel
import com.github.pramahalqavi.adapterdelegate.model.BindingDelegateItem
import com.github.pramahalqavi.adapterdelegate.model.viewholder.BindingDelegateViewHolder

class OneDelegate : BindingDelegateItem<OneModel, ItemOneBinding>(ItemOneBinding::inflate) {
    override fun onBind(item: OneModel, holder: BindingDelegateViewHolder<ItemOneBinding>) {
        with(holder.binding) {
            tvOne.text = item.fieldOne
        }
    }

    override fun isForViewType(item: Any, position: Int): Boolean = item is OneModel
}