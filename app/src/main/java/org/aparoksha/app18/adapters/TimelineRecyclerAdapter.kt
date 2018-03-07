package org.aparoksha.app18.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.item_timeline_line_padding.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.models.VectorDrawableUtils
import org.aparoksha.app18.utils.GlideApp

class TimelineRecyclerAdapter(val context: Context) : RecyclerView.Adapter<TimelineRecyclerAdapter.TimeLineViewHolder>() {

    private var items: MutableList<Event>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder =
            TimeLineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_line_padding,parent,false),viewType)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        holder.bind(items.get(position),context)
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    init {
        items = mutableListOf()
    }

    fun addEvents(item: List<Event>) {
        this.items.addAll(item)
        notifyDataSetChanged()
    }

    fun reset() {
        this.items.removeAll(items)
    }

    class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.time_marker.initLine(viewType)
        }

        fun bind(event: Event,context: Context) {

            itemView.name.text = event.name
            itemView.time.text = event.timestamp.toString()

            GlideApp.with(context)
                    .load(event.imageUrl)
                    .circleCrop()
                    .placeholder(R.drawable.logo)
                    .into(itemView.eventImage)

            if (System.currentTimeMillis() - event.timestamp > 3600000) {
                itemView.time_marker.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_inactive, android.R.color.darker_gray))
            } else if (System.currentTimeMillis() - event.timestamp > 0 && System.currentTimeMillis() - event.timestamp < 3600000) {
                itemView.time_marker.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_active, R.color.colorPrimary))
            } else {
                itemView.time_marker.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_marker), ContextCompat.getColor(context, R.color.colorPrimary))
            }
        }
    }
}
