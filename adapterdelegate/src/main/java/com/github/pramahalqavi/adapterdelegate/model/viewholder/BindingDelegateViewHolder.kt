package com.github.pramahalqavi.adapterdelegate.model.viewholder

import androidx.viewbinding.ViewBinding

class BindingDelegateViewHolder<T: ViewBinding>(val binding: T) : DelegateViewHolder(binding.root)