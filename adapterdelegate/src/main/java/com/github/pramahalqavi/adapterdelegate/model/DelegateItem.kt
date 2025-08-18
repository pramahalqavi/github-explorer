package com.github.pramahalqavi.adapterdelegate.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.github.pramahalqavi.adapterdelegate.model.viewholder.DelegateViewHolder

abstract class DelegateItem<M>(
    @LayoutRes private val layoutId: Int
) : Delegate<M, DelegateViewHolder> {
    override fun onCreateViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder {
        val viewHolder = DelegateViewHolder(LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false))
        onViewHolderCreated(viewHolder)
        return viewHolder
    }

    open fun onViewHolderCreated(holder: DelegateViewHolder) {
        // no-op
    }
}