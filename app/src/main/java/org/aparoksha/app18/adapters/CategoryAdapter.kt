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

class CategoryAdapter(val context: Context): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var categories: List<String>
    var events: List<Event>

    init {
        categories = listOf("informal","informal","informal","informal")
        events = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_container,parent,false))

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories.get(position))
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        fun bind(category: String) {
            val informalList = events.map { item -> if(item.categories.contains(category)) item else null}.filterNotNull()
            itemView.textView.text = category

            itemView.eventsRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            itemView.eventsRecyclerView.isDrawingCacheEnabled = true
            itemView.eventsRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

            val adapter = EventsAdapter(context)
            itemView.eventsRecyclerView.adapter = adapter

            adapter.updateEvents(informalList)
        }
    }

    fun updateEvents(events: List<Event>) {
        this.events = events
        notifyDataSetChanged()
    }
}