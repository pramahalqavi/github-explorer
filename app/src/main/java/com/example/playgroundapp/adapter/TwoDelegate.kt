package com.example.playgroundapp.adapter

import com.example.playgroundapp.databinding.ItemTwoBinding
import com.example.playgroundapp.model.TwoModel
import com.github.pramahalqavi.adapterdelegate.model.BindingDelegateItem
import com.github.pramahalqavi.adapterdelegate.model.viewholder.BindingDelegateViewHolder

class TwoDelegate : BindingDelegateItem<TwoModel, ItemTwoBinding>(ItemTwoBinding::inflate) {
    override fun onBind(item: TwoModel, holder: BindingDelegateViewHolder<ItemTwoBinding>) {
        with(holder.binding) {
            tvOne.text = item.fieldOne
            tvTwo.text = item.fieldTwo
        }
    }

    override fun isForViewType(item: Any, position: Int): Boolean = item is TwoModel
}