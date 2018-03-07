package org.aparoksha.app18.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_timeline.*
import org.aparoksha.app18.R
import org.aparoksha.app18.ViewModels.EventsViewModel
import org.aparoksha.app18.adapters.TimelineRecyclerAdapter
import org.aparoksha.app18.models.Aparoksha
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.models.TimelineEvents
import org.aparoksha.app18.models.Timepoint
import org.aparoksha.app18.utils.AppDB


/**
 * Created by akshat on 5/3/18.
 */

class TimelineFragment: Fragment() {

    private lateinit var eventViewModel: EventsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_timeline,container,false)


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventViewModel = EventsViewModel.create(activity.application)
        eventViewModel.getFromDB(context)
        eventViewModel.getEvents(context,activity)

        timeline_recycler_view.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)

        val adapter = TimelineRecyclerAdapter(context)
        timeline_recycler_view.isDrawingCacheEnabled = true
        timeline_recycler_view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        timeline_recycler_view.adapter = adapter

        eventViewModel.events.observe(this, Observer {
            it?.let {
                if (!it.isEmpty()) {
                    val list: MutableList<Event> = it as MutableList<Event>
                    list.sortBy { it.timestamp }

                    adapter.reset()
                    adapter.addEvents(list)
                }
            }
        })
    }
}