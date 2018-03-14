package org.aparoksha.app18.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.item_timeline_line_padding.view.*
import org.aparoksha.app18.GlideApp
import org.aparoksha.app18.R
import org.aparoksha.app18.activities.EventDetailActivity
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.models.VectorDrawableUtils
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class TimelineRecyclerAdapter : RecyclerView.Adapter<TimelineRecyclerAdapter.TimeLineViewHolder>() {

    private var items: List<Event> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder =
            TimeLineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_line_padding,parent,false),viewType)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    fun updateEvents(items: List<Event>) {
        this.items = items.sortedBy { it.timestamp }
        notifyDataSetChanged()
    }

    class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.time_marker.initLine(viewType)
        }

        fun bind(event: Event) {

            with(itemView) {
                eventNameTV.text = event.name
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
                if (event.timestamp < 20000L) {
                    eventDateTV.visibility = View.INVISIBLE
                    eventTimeTV.text = "Online Event"
                } else {
                    calendar.timeInMillis = event.timestamp.times(1000L)

                    eventDateTV.visibility = View.VISIBLE
                    var sdf = SimpleDateFormat("MMMM d")
                    sdf.timeZone = TimeZone.getTimeZone("Asia/India")
                    eventDateTV.text = sdf.format(calendar.time)

                    sdf = SimpleDateFormat("hh:mm a")
                    sdf.timeZone = TimeZone.getTimeZone("Asia/India")
                    eventTimeTV.text = sdf.format(calendar.time)
                }

                GlideApp.with(itemView.context)
                        .load(event.imageUrl)
                        .placeholder(R.drawable.logo)
                        .into(itemView.eventImage)

                when {
                    System.currentTimeMillis() - event.timestamp.times(1000L) > 3600000 ->
                        time_marker.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_inactive, android.R.color.darker_gray))
                    System.currentTimeMillis() - event.timestamp.times(1000L) in 1..3599999 ->
                        time_marker.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_active, android.R.color.darker_gray))
                    else ->
                        time_marker.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_marker), android.R.color.darker_gray)
                }

                itemView.cardView.setOnClickListener {
                    context.startActivity<EventDetailActivity>("id" to event.id)
                }
            }
        }
    }
}
