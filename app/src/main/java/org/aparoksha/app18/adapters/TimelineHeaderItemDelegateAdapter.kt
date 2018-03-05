package org.aparoksha.app18.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_timeline_header.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Aparoksha
import org.aparoksha.app18.utils.ViewType

/**
 * Created by akshat on 5/3/18.
 */
class TimelineHeaderItemDelegateAdapter: ViewTypeDelegateAdapter {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as HeaderItemViewHolder
        holder.bind(item as Aparoksha)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = HeaderItemViewHolder(parent)

    inner class HeaderItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_header, parent, false)) {

        fun bind(item: Aparoksha) = with(itemView) {
            description.text = item.description
        }

    }
}