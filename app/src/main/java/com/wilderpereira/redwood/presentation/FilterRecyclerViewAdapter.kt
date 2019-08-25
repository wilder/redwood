package com.wilderpereira.redwood.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wilderpereira.redwood.R
import com.wilderpereira.redwood.domain.ImageFilter
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterRecyclerViewAdapter(private val filters: List<ImageFilter>, private val action: (ImageFilter) -> Unit)
    : RecyclerView.Adapter<FilterRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_filter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filters.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filters[position], action)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(row: ImageFilter, action: (ImageFilter) -> Unit) {
            itemView.run {
                itemTv.text = row.getName()
                setOnClickListener { action.invoke(row) }
            }
        }
    }

}