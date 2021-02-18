package com.waxym.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.waxym.databinding.ItemStatsChartBinding
import com.waxym.databinding.ItemStatsGraphBinding
import com.waxym.ui.viewmodel.FizzBuzzStatsViewModel.UIO

class FizzBuzzStatsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data = mutableListOf<UIO>()

    fun setData(data: List<UIO>) {
        this.data.clear()
        this.data.addAll(data)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = when (data.getOrNull(position)) {
        is UIO.Graph -> VIEW_TYPE_GRAPH
        is UIO.Char -> VIEW_TYPE_CHART
        else -> super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_GRAPH -> GraphViewHolder(ItemStatsGraphBinding.inflate(inflater, parent, false))
            VIEW_TYPE_CHART -> CharViewHolder(ItemStatsChartBinding.inflate(inflater, parent, false))
            else -> super.createViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data.getOrNull(position)
        when {
            item is UIO.Graph && holder is GraphViewHolder -> {
                holder.binding.graph.setPercents(item.data)
            }
            item is UIO.Char && holder is CharViewHolder -> {
                holder.binding.color.setBackgroundColor(item.color)
                holder.binding.fizzMultiple.text = item.fizzMultiple
                holder.binding.fizzLabel.text = item.fizzLabel
                holder.binding.buzzMultiple.text = item.buzzMultiple
                holder.binding.buzzLabel.text = item.buzzLabel
                holder.binding.limit.text = item.limit
            }
        }
    }

    class GraphViewHolder(val binding: ItemStatsGraphBinding) : RecyclerView.ViewHolder(binding.root)

    class CharViewHolder(val binding: ItemStatsChartBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val VIEW_TYPE_GRAPH = 1
        const val VIEW_TYPE_CHART = 2
    }
}