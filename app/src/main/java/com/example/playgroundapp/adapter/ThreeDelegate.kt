package com.example.playgroundapp.adapter

import com.example.playgroundapp.databinding.ItemThreeBinding
import com.example.playgroundapp.model.ThreeModel
import com.github.pramahalqavi.adapterdelegate.model.BindingDelegateItem
import com.github.pramahalqavi.adapterdelegate.model.viewholder.BindingDelegateViewHolder

class ThreeDelegate : BindingDelegateItem<ThreeModel, ItemThreeBinding>(ItemThreeBinding::inflate) {
    override fun onBind(item: ThreeModel, holder: BindingDelegateViewHolder<ItemThreeBinding>) {
        with(holder.binding) {
            tvOne.text = item.fieldOne
            tvTwo.text = item.fieldTwo
            tvThree.text = item.fieldThree
        }
    }

    override fun isForViewType(item: Any, position: Int): Boolean = item is ThreeModel
}