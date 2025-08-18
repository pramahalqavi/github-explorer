package com.github.pramahalqavi.adapterdelegate.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.pramahalqavi.adapterdelegate.model.Delegate

class DelegateManager<MODEL, VHOLDER> {
    private val delegateMap = HashMap<Int, Delegate<MODEL, VHOLDER>>()

    fun registerDelegate(delegate: Delegate<MODEL, VHOLDER>) {
        val viewType = getViewType(delegate)
        if (!delegateMap.containsKey(viewType)) {
            delegateMap[viewType] = delegate
        }
    }

    private fun getViewType(delegate: Delegate<MODEL, VHOLDER>): Int {
        return delegate::class.hashCode()
    }

    private fun getDelegate(itemViewType: Int): Delegate<MODEL, VHOLDER> {
        return delegateMap[itemViewType] ?: throw IllegalStateException("Delegate for viewType $itemViewType is not registered")
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegate = getDelegate(viewType)
        return delegate.onCreateViewHolder(parent)
    }

    @Suppress("UNCHECKED_CAST")
    fun onBindViewHolder(itemModel: MODEL, holder: RecyclerView.ViewHolder) {
        val delegate = getDelegate(holder.itemViewType)
        delegate.onBind(itemModel, holder as VHOLDER)
    }

    fun getItemViewType(items: List<MODEL>, position: Int): Int {
        val item = items[position]
        for ((viewType, delegate) in delegateMap) {
            if (delegate.isForViewType(item as Any, position)) {
                return viewType
            }
        }
        throw IllegalStateException("No registered delegate for item at position $position")
    }

    fun getItemId(items: List<MODEL>, position: Int): Long {
        val delegate = getDelegate(getItemViewType(items, position))
        return delegate.getItemId(items[position])
    }

    @Suppress("UNCHECKED_CAST")
    fun onViewAttachedToWindow(items: List<MODEL>, holder: RecyclerView.ViewHolder) {
        val delegate = getDelegate(holder.itemViewType)
        holder.bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.let { position ->
            delegate.onViewAttachedToWindow(items[position], holder as VHOLDER)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val delegate = getDelegate(holder.itemViewType)
        delegate.onViewDetachedFromWindow(holder as VHOLDER)
    }

    @Suppress("UNCHECKED_CAST")
    fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        val delegate = getDelegate(holder.itemViewType)
        delegate.onViewRecycled(holder as VHOLDER)
    }
}