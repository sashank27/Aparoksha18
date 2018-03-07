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

        val timelineRecyclerAdapter = TimelineRecyclerAdapter()
        timeline_recycler_view.adapter = timelineRecyclerAdapter
        timeline_recycler_view.layoutManager = LinearLayoutManager(context)

        eventViewModel.events.observe(this, Observer {
            it?.let {
                if (!it.isEmpty()) {
                    timelineRecyclerAdapter.reset()
                    val list: MutableList<Event> = it as MutableList<Event>
                    list.sortBy { it.timestamp }
                    timelineRecyclerAdapter.addHeader(Aparoksha("Aparoksha Events","TechFest","16-18 March","IIIT Allahabad"))
                    var size = 0
                    for (i in it) {
                        size++
                        timelineRecyclerAdapter.addTimepoint(Timepoint("test"))
                        timelineRecyclerAdapter.addEvent(TimelineEvents(i.name, i.description, i.timestamp, i.imageUrl, size == it.size))
                    }
                }
            }
        })
    }

    /*override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coreViewModel = EventsViewModel.create(activity.application)
        coreViewModel.getFromDB(context)
        coreViewModel.getEvents(context,activity)

        coreViewModel.events.observe(this, Observer {
            it?.let {
                if (!it.isEmpty()) {

                }
            }
        })
    }*/
}