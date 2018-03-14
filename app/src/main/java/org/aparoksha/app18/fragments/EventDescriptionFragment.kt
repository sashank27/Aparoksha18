package org.aparoksha.app18.fragments

import android.arch.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_description.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.viewModels.AppViewModel

/**
 * Created by sashank on 10/3/18.
 */

class EventDescriptionFragment : Fragment() {

    private var event : Event = Event()

    fun newInstance(eventID: Long): EventDescriptionFragment {

        val args = Bundle()
        args.putLong("eventId", eventID)
        val fragment = EventDescriptionFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventID = arguments.getLong("eventId")
        val eventViewModel = AppViewModel.create(activity.application)
        eventViewModel.getEvents(activity,false)

        eventViewModel.events.observe(this, Observer {
            it?.let {
                val temp = it.find { it.id == eventID }
                if (it.find { temp?.id == eventID } != null)
                    event = temp!!

                descriptionTV.text = event.description
                facebookLinkTV.text = event.facebookEventLink

                facebookLinkLayout.visibility = if (event.facebookEventLink == "") View.GONE else View.VISIBLE

                if (event.additionalInfo.isEmpty()) {
                    additionalInfoLayout.visibility = View.GONE
                } else {
                    additionalInfoTV.text = event.additionalInfo.joinToString { "\n" }
                }

                facebookLinkLayout.setOnClickListener({
                    val url = event.facebookEventLink
                    try {
                        activity.packageManager.getPackageInfo("com.facebook.katana", 0)
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=$url"))
                        activity.startActivity(intent)
                    } catch (e: Exception) {
                        val builder = CustomTabsIntent.Builder()
                        val customTabsIntent = builder.build()
                        customTabsIntent.launchUrl(activity, Uri.parse(url))
                    }
                })
            }
        })
    }
}