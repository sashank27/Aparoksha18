package org.aparoksha.app18.adapters

import android.content.Context
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.event_container.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.utils.GlideApp

/**
 * Created by akshat on 7/3/18.
 */

class EventsAdapter(val context:Context): RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    var eventsList: List<Event> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_container,parent,false))

    override fun getItemCount(): Int = eventsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventsList.get(position))
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(event: Event) {
            itemView.eventNameTV.text = event.name
            itemView.eventTimeTV.text = event.timestamp.toString()

            GlideApp.with(context)
                    .load(event.imageUrl)
                    .circleCrop()
                    .placeholder(R.drawable.logo)
                    .into(itemView.eventImageView)
        }
    }

    fun updateEvents(events: List<Event>) {
        eventsList = events
        notifyDataSetChanged()
    }


}