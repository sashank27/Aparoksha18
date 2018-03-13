package org.aparoksha.app18.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.category_container.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Event

/**
 * Created by akshat on 8/3/18.
 */

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var events = listOf<Pair<String, List<Event>>>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_container, parent, false))

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pair: Pair<String, List<Event>>) {
            val (category, events) = pair

            with(itemView) {
                itemView.textView.text = category.capitalize()
                itemView.eventsRecyclerView.layoutManager = LinearLayoutManager(context,
                        LinearLayoutManager.HORIZONTAL, false)

                val adapter = EventsAdapter()
                itemView.eventsRecyclerView.adapter = adapter
                adapter.updateEvents(events)
            }
        }
    }

    fun updateEvents(events: List<Event>) {
        val categories = events.map { it.categories }.flatten().distinct()
        this.events = categories.map { category ->
            category to events.filter { it.categories.contains(category) }
        }.toList()
        notifyDataSetChanged()
    }

}