package org.aparoksha.app18.fragments

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_developer.*
import org.aparoksha.app18.R

/**
 * Created by sashank on 11/3/18.
 */

class DeveloperFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_developer, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pranjal.setOnClickListener {
            openChromeTab("https://github.com/betterclever")
        }

        sashank.setOnClickListener {
            openChromeTab("https://github.com/sashank27")
        }

        akshat.setOnClickListener {
            openChromeTab("https://github.com/dabbler011")
        }
    }

    private fun openChromeTab(url: String){
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(activity, Uri.parse(url))
    }
}