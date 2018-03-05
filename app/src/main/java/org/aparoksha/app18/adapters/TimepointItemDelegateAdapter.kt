package org.aparoksha.app18.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Timepoint
import org.aparoksha.app18.utils.ViewType

class TimepointItemDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = PointItemViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as PointItemViewHolder
        holder.bind(item as Timepoint)
    }

    inner class PointItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_line, parent, false)) {

        fun bind(item: Timepoint) = with(itemView) {
            //time.text = item.timepoint
        }

    }

}


