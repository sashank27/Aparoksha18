package org.aparoksha.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import org.aparoksha.app18.R
import org.aparoksha.app18.viewModels.EventsViewModel
import org.aparoksha.app18.adapters.FlagshipViewPagerAdapter
import org.aparoksha.app18.ui.ParallaxPageTransformer

/**
 * Created by sashank on 3/3/18.
 */

class HomeFragment : Fragment() {

    private val flagshipData = arrayOf(R.drawable.hint, R.drawable.fragfest, R.drawable.codered,
            R.drawable.grayhound, R.drawable.topbot, R.drawable.bootroot, R.drawable.humblefool)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventViewModel = EventsViewModel.create(activity.application)
        eventViewModel.getFromDB(context)
        eventViewModel.getEvents(context,activity)

        val adapter = FlagshipViewPagerAdapter(childFragmentManager, flagshipData)

        viewPager.adapter = adapter
        viewPager.setPageTransformer(true, ParallaxPageTransformer())
        viewPager.startAutoScroll(1000)
        viewPager.setAutoScrollDurationFactor(15.0)

    }

}
