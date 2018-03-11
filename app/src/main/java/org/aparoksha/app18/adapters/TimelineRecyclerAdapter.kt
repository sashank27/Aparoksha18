package org.aparoksha.app18.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.item_timeline_line_padding.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.models.VectorDrawableUtils
import org.aparoksha.app18.GlideApp
import org.aparoksha.app18.R.id.eventLocationTV
import org.aparoksha.app18.activities.EventDetailActivity
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class TimelineRecyclerAdapter(val context: Context) : RecyclerView.Adapter<TimelineRecyclerAdapter.TimeLineViewHolder>() {

    private var items: List<Event> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder =
            TimeLineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_line_padding,parent,false),viewType)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        holder.bind(items[position],context)
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    fun addEvents(items: List<Event>) {
        this.items = items.sortedBy { it.timestamp }
        notifyDataSetChanged()
    }

    class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.time_marker.initLine(viewType)
        }

        fun bind(event: Event,context: Context) {

            itemView.eventNameTV.text = event.name
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
            if (event.timestamp < 100L) {
                itemView.eventTimeTV.text = "Online Event"
            } else {
                calendar.timeInMillis = event.timestamp.times(1000L)

                var sdf = SimpleDateFormat("MMMM d")
                sdf.timeZone = TimeZone.getTimeZone("Asia/India")
                itemView.eventDateTV.text = sdf.format(calendar.time)

                sdf = SimpleDateFormat("hh:mm a")
                sdf.timeZone = TimeZone.getTimeZone("Asia/India")
                itemView.eventTimeTV.text = sdf.format(calendar.time)
            }

            GlideApp.with(context)
                    .load(event.imageUrl)
                    .placeholder(R.drawable.logo)
                    .into(itemView.eventImage)

            when {
                System.currentTimeMillis() - event.timestamp > 3600000 -> itemView.time_marker.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_inactive, android.R.color.darker_gray))
                System.currentTimeMillis() - event.timestamp in 1..3599999 -> itemView.time_marker.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_active, R.color.colorPrimary))
                else -> itemView.time_marker.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_marker), ContextCompat.getColor(context, R.color.colorPrimary))
            }

            itemView.setOnClickListener { itemView.context.startActivity<EventDetailActivity>("id" to event.id) }
        }
    }
}
