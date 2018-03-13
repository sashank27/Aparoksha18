package org.aparoksha.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_events_all.view.*
import org.aparoksha.app18.R

/**
 * Created by akshat on 13/3/18.
 */

class EventContainerFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_events_all, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(TimelineFragment())
        fragmentList.add(EventsFragment())
        fragmentList.add(TalksFragment())

        val mPagerAdapter = EventFragmentAdapter(activity.supportFragmentManager, fragmentList)
        view.pager.adapter = mPagerAdapter
        view.tabLayout.setupWithViewPager(view.pager)

    }

    private inner class EventFragmentAdapter internal constructor(fragmentManager: FragmentManager, fragmentList: List<Fragment>) : FragmentStatePagerAdapter(fragmentManager) {

        private val titles = arrayOf("Timeline", "Events", "Tech Talks")
        internal var fragments: MutableList<Fragment> = ArrayList()

        init {
            fragments.addAll(fragmentList)
        }

        override fun getItem(position: Int): Fragment {
            Log.e("FRAGMENT", Integer.toString(position))
            return fragments[position]
        }

        override fun getCount(): Int = titles.size

        override fun getPageTitle(position: Int): CharSequence = titles[position]
    }
}