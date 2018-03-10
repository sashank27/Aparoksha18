package org.aparoksha.app18.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import org.aparoksha.app18.fragments.EventDescriptionFragment
import org.aparoksha.app18.fragments.OrganizersFragment
import org.aparoksha.app18.fragments.UpdatesFragment

/**
 * Created by sashank on 10/3/18.
 */

class EventDetailViewPagerAdapter(fm : FragmentManager, eventId : Long) : FragmentStatePagerAdapter(fm) {

    private val fragments = listOf(EventDescriptionFragment().newInstance(eventId),OrganizersFragment().newInstance(eventId),UpdatesFragment())

    override fun getItem(position: Int) : Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

}