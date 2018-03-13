package org.aparoksha.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_talks.*
import org.aparoksha.app18.R
import org.aparoksha.app18.adapters.TalksViewAdapter


/**
 * Created by akshat on 13/3/18.
 */

class TalksFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
         inflater.inflate(R.layout.fragment_talks,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TalksViewAdapter(childFragmentManager)

        deck_pager.adapter = adapter

    }
}