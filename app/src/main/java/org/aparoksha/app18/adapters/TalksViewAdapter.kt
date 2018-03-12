package org.aparoksha.app18.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import org.aparoksha.app18.fragments.TalkContainer

/**
 * Created by akshat on 13/3/18.
 */

class TalksViewAdapter (fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = TalkContainer.newInstance(position)

    override fun getCount(): Int = 3

}