package org.aparoksha.app18.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_timeline.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.aparoksha.app18.R
import org.aparoksha.app18.adapters.TimelineRecyclerAdapter
import org.aparoksha.app18.utils.showAlert
import org.aparoksha.app18.viewModels.AppViewModel


/**
 * Created by akshat on 5/3/18.
 */

class TimelineFragment : Fragment() {

    private lateinit var eventViewModel: AppViewModel
    private lateinit var adapter: TimelineRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventViewModel = AppViewModel.create(activity.application)
        adapter = TimelineRecyclerAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_timeline, container, false)


    override fun onResume() {
        super.onResume()

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch(UI) {
            val fragment = this@TimelineFragment
            eventViewModel.getEvents()

            timeline_recycler_view.layoutManager = LinearLayoutManager(fragment.context,
                    LinearLayoutManager.VERTICAL, false)

            timeline_recycler_view.adapter = null
            timeline_recycler_view.adapter = adapter
            eventViewModel.events.value?.apply {
                if(isNotEmpty()) adapter.updateEvents(this)
            }

            eventViewModel.events.observe(fragment, Observer {
                it?.let {
                    if (it.isNotEmpty()) {
                        adapter.updateEvents(it)
                    }
                }
            })

            eventViewModel.dataFetchFailed.observe(fragment, Observer {
                it?.let {
                    if (it) showAlert(activity)
                }
            })
        }
    }
}