package com.waxym.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.waxym.databinding.ItemFizzbuzzBinding

class FizzBuzzItemAdapter : RecyclerView.Adapter<FizzBuzzItemAdapter.FizzBuzzItemViewHolder>() {
    private val data = mutableListOf<String>()

    fun setData(data: List<String>) {
        this.data.clear()
        this.data.addAll(data)
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FizzBuzzItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FizzBuzzItemViewHolder(ItemFizzbuzzBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FizzBuzzItemViewHolder, position: Int) {
        data.getOrNull(position)?.let { item ->
            holder.binding.label.text = item
        }
    }

    class FizzBuzzItemViewHolder(val binding: ItemFizzbuzzBinding) : RecyclerView.ViewHolder(binding.root)
}