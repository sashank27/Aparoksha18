package org.aparoksha.app18.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_events.*
import org.aparoksha.app18.R
import org.aparoksha.app18.viewModels.EventsViewModel
import org.aparoksha.app18.adapters.EventsAdapter
import org.aparoksha.app18.models.Event

/**
 * Created by akshat on 7/3/18.
 */

class EventsFragment: Fragment() {

    private lateinit var eventViewModel: EventsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_events,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventViewModel = EventsViewModel.create(activity.application)
        eventViewModel.getFromDB(context)
        eventViewModel.getEvents(context,activity)

        firstRecyclerView.isDrawingCacheEnabled = true
        firstRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

        firstRecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)

        secondRecyclerView.isDrawingCacheEnabled = true
        secondRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

        secondRecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)

        val firstAdapter = EventsAdapter(context)
        val secondAdapter = EventsAdapter(context)
        firstRecyclerView.adapter = firstAdapter
        secondRecyclerView.adapter = secondAdapter

        eventViewModel.events.observe(this, Observer {
            it?.let {
                if (!it.isEmpty()) {
                    val list: MutableList<Event> = it as MutableList<Event>
                    list.sortBy { it.timestamp }
                    val informalList = list.map { item -> if(item.categories.contains("informal")) item else null}.filterNotNull()
                    firstAdapter.updateEvents(informalList)
                    secondAdapter.updateEvents(informalList)
                }
            }
        })
    }
}