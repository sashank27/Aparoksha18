package org.aparoksha.app18.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.event_container.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.GlideApp
import org.aparoksha.app18.activities.EventDetailActivity
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by akshat on 7/3/18.
 */

class EventsAdapter(val context:Context): RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    private var eventsList: List<Event> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_container,parent,false))

    override fun getItemCount(): Int = eventsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventsList[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(event: Event) {
            itemView.eventNameTV.text = event.name

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
            if (event.timestamp < 100L) {
                itemView.eventTimeTV.text = "Online Event"
            } else {
                calendar.timeInMillis = event.timestamp.times(1000L)

                val sdf = SimpleDateFormat("MMM d, hh:mm a")
                sdf.timeZone = TimeZone.getTimeZone("Asia/India")
                itemView.eventTimeTV.text = sdf.format(calendar.time)
            }

            GlideApp.with(context)
                    .load(event.imageUrl)
                    .placeholder(R.drawable.logo)
                    .into(itemView.eventImage)

            itemView.setOnClickListener { itemView.context.startActivity<EventDetailActivity>("id" to event.id) }
        }
    }

    fun updateEvents(events: List<Event>) {
        eventsList = events
        notifyDataSetChanged()
    }
}