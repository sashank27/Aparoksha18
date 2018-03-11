package org.aparoksha.app18.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_team.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.adapters.TeamAdapter
import org.aparoksha.app18.models.Person
import org.aparoksha.app18.utils.showAlert
import org.aparoksha.app18.viewModels.AppViewModel

/**
 * Created by sashank on 11/3/18.
 */

class TeamFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_team, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventViewModel = AppViewModel.create(activity.application)
        eventViewModel.getTeam()

        val adapter = TeamAdapter(context)

        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = adapter

        eventViewModel.team.observe(this, Observer {
            it?.let {
                if (!it.isEmpty()) {
                    val list: MutableList<Person> = it as MutableList<Person>
                    adapter.updateTeam(list)
                }
            }
        })

        eventViewModel.dataFetchFailed.observe(this, Observer {
            it?.let {
                if(it) showAlert(activity)
            }
        })
    }
}
