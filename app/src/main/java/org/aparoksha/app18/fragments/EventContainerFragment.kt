package org.aparoksha.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_events_all.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.aparoksha.app18.R

/**
 * Created by akshat on 13/3/18.
 */

class EventContainerFragment : Fragment() {

    private lateinit var adapter: FragmentStatePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = EventFragmentAdapter(activity.supportFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_events_all, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch(UI) {
            pager.adapter = null
            pager.adapter = adapter
            pager.currentItem = 0
            tabLayout.setupWithViewPager(pager)
        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private inner class EventFragmentAdapter
    internal constructor(fragmentManager: FragmentManager)
        : FragmentStatePagerAdapter(fragmentManager) {

        private val titles = arrayOf("Timeline", "Events", "Tech Talks")
        internal var fragments = listOf(TimelineFragment(), EventsFragment(), TalksFragment())

        override fun getItem(position: Int): Fragment {
            Log.e("FRAGMENT", Integer.toString(position))
            return fragments[position]
        }

        override fun getCount(): Int = titles.size

        override fun getPageTitle(position: Int): CharSequence = titles[position]
    }
}