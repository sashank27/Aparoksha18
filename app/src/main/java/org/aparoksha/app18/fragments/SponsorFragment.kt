package org.aparoksha.app18.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_sponsors.*
import org.aparoksha.app18.R
import org.aparoksha.app18.adapters.SponsorsAdapter
import org.aparoksha.app18.models.Sponsor
import org.aparoksha.app18.utils.showAlert
import org.aparoksha.app18.viewModels.AppViewModel

/**
 * Created by sashank on 11/3/18.
 */

class SponsorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_sponsors, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventViewModel = AppViewModel.create(activity.application)
        eventViewModel.getSponsors(activity)

        val adapter = SponsorsAdapter(activity)
        sponsorsList.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        sponsorsList.adapter = adapter

        eventViewModel.sponsor.observe(this, Observer {
            it?.let {
                if (!it.isEmpty()) {
                    val list: MutableList<Sponsor> = it as MutableList<Sponsor>
                    adapter.updateSponsors(list)
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