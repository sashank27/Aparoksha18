package org.aparoksha.app18.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import org.aparoksha.app18.fragments.FlagshipFragment

/**
 * Created by sashank on 7/3/18.
 */

class FlagshipViewPagerAdapter(fm : FragmentManager, val data : Array<Int>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = FlagshipFragment.newInstance(data[position])

    override fun getCount(): Int = data.size

}
