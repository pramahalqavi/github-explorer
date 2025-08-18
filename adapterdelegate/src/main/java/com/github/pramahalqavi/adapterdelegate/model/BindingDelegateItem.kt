package com.github.pramahalqavi.adapterdelegate.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.github.pramahalqavi.adapterdelegate.model.viewholder.BindingDelegateViewHolder

abstract class BindingDelegateItem<M, B: ViewBinding>(
    private val bindingConstructor: ((LayoutInflater, ViewGroup, Boolean) -> ViewBinding)
): Delegate<M, BindingDelegateViewHolder<B>> {
    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder {
        val viewHolder = BindingDelegateViewHolder(bindingConstructor(LayoutInflater.from(viewGroup.context), viewGroup, false) as B)
        onViewHolderCreated(viewHolder)
        return viewHolder
    }

    open fun onViewHolderCreated(holder: BindingDelegateViewHolder<B>) {}
}