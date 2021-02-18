package com.waxym.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.waxym.databinding.ItemFizzbuzzBinding

class FizzBuzzItemAdapter : PagedListAdapter<String, FizzBuzzItemAdapter.FizzBuzzItemViewHolder>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FizzBuzzItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FizzBuzzItemViewHolder(ItemFizzbuzzBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FizzBuzzItemViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.binding.label.text = item
        }
    }

    class FizzBuzzItemViewHolder(val binding: ItemFizzbuzzBinding) : RecyclerView.ViewHolder(binding.root)
}