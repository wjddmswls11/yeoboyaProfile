package com.example.yeoboyaprofile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class IndicatorAdapter(private val itemCount : Int) : RecyclerView.Adapter<IndicatorAdapter.IndicatorViewHolder>() {
    private var currentPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_indicator, parent, false)
        return IndicatorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    override fun onBindViewHolder(holder: IndicatorViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setCurrentPosition(position: Int) {
        currentPosition = position
        notifyDataSetChanged()
    }

    inner class IndicatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val indicatorView: View = itemView.findViewById(R.id.indicator_item)

        fun bind(position: Int) {
            if (position == currentPosition) {
                indicatorView.setBackgroundResource(R.drawable.indicator_selected)
            } else {
                indicatorView.setBackgroundResource(R.drawable.indicator_unselected)
            }
        }
    }
}

