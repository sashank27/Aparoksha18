package org.aparoksha.app18.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.aparoksha.app18.R
import org.aparoksha.app18.adapters.CategoryAdapter
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.utils.showAlert
import org.aparoksha.app18.viewModels.AppViewModel

/**
 * Created by akshat on 7/3/18.
 */

class EventsFragment: Fragment() {

    private lateinit var appViewModel: AppViewModel
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appViewModel = AppViewModel.create(activity.application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_events,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch(UI) {
            val fragment = this@EventsFragment
            appViewModel.getEvents()
            categoryRecyclerView.layoutManager = LinearLayoutManager(fragment.context,
                    LinearLayoutManager.VERTICAL,false)

            adapter = CategoryAdapter(fragment.context)
            categoryRecyclerView.adapter = adapter

            appViewModel.events.observe(fragment, Observer {
                it?.let {
                    if (!it.isEmpty()) {
                        adapter.updateEvents(it)
                    }
                }
            })

            appViewModel.dataFetchFailed.observe(fragment, Observer {
                it?.let {
                    if(it) showAlert(activity)
                }
            })
        }
    }
}