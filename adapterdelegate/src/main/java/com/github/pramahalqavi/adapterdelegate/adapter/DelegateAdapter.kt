package com.github.pramahalqavi.adapterdelegate.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.pramahalqavi.adapterdelegate.model.Delegate

class DelegateAdapter(
    delegates: List<Delegate<*, *>>,
    diffCallback: DiffUtil.ItemCallback<Any> = DefaultDiffCallback()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val delegateManager = DelegateManager<Any, Any>()
    private val differ: AsyncListDiffer<Any> = AsyncListDiffer(this, diffCallback)

    init {
        delegates.forEach { delegate ->
            @Suppress("UNCHECKED_CAST")
            delegateManager.registerDelegate(delegate as Delegate<Any, Any>)
        }
    }

    fun submitList(items: List<Any>, commitCallback: Runnable? = null) {
        differ.submitList(items, commitCallback)
    }

    fun clearList(commitCallback: Runnable? = null) {
        differ.submitList(null, commitCallback)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateManager.onBindViewHolder(differ.currentList[position], holder)
    }

    override fun getItemViewType(position: Int): Int {
        return delegateManager.getItemViewType(differ.currentList, position)
    }

    override fun getItemId(position: Int): Long {
        return delegateManager.getItemId(differ.currentList, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        delegateManager.onViewAttachedToWindow(differ.currentList, holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        delegateManager.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        delegateManager.onViewRecycled(holder)
    }

}