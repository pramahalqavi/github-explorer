package com.github.pramahalqavi.adapterdelegate.model

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface Delegate<T, V> {
    fun onCreateViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder
    fun onBind(item: T, holder: V)
    fun isForViewType(item: Any, position: Int): Boolean
    fun onViewAttachedToWindow(item: T, holder: V) = Unit
    fun onViewDetachedFromWindow(holder: V) = Unit
    fun getItemId(item: T): Long = RecyclerView.NO_ID
    fun onViewRecycled(holder: V) = Unit
}