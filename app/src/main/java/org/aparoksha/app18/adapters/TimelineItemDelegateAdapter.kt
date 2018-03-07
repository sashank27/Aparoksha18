package org.aparoksha.app18.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_timeline.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.TimelineEvents
import org.aparoksha.app18.utils.ViewType
import pl.hypeapp.materialtimelineview.MaterialTimelineView

/**
 * Created by akshat on 5/3/18.
 */

class TimelineItemDelegateAdapter: ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = WeatherItemViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as WeatherItemViewHolder
        holder.bind(item as TimelineEvents)
    }

    inner class WeatherItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_timeline, parent, false)) {

        fun bind(item: TimelineEvents) = with(itemView) {
            if (item.isLastItem) {
                item_weather_timeline.position = MaterialTimelineView.POSITION_LAST
            }

            name.text = item.eventName
            description.text = item.description
            time.text = item.timestamp.toString()
        }

    }
}