package org.aparoksha.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_timeline.*
import org.aparoksha.app18.R
import org.aparoksha.app18.adapters.TimelineRecyclerAdapter
import org.aparoksha.app18.models.Aparoksha
import org.aparoksha.app18.models.TimelineEvents
import org.aparoksha.app18.models.Timepoint
import org.aparoksha.app18.utils.AppDB


/**
 * Created by akshat on 5/3/18.
 */

class TimelineFragment: Fragment() {

    lateinit var timelineRecyclerAdapter: TimelineRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timeline,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDb = AppDB.getInstance(context)
        val list = appDb.getAllEvents()
        list.sortBy { it.timestamp }
        var size = 0

        timelineRecyclerAdapter = TimelineRecyclerAdapter()
        timeline_recycler_view.adapter = timelineRecyclerAdapter
        timeline_recycler_view.layoutManager = LinearLayoutManager(context)
        timelineRecyclerAdapter.addWeatherHeader(Aparoksha("Aparoksha Events","TechFest","16-18 March","IIIT Allahabad"))
        for (i in list) {
            size++
            timelineRecyclerAdapter.addTimepoint(Timepoint("test"))
            timelineRecyclerAdapter.addWeather(TimelineEvents(i.name,i.description,i.timestamp,i.imageUrl,size == list.size))
        }

    }
}