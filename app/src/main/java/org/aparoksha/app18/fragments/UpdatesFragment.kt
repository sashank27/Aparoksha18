package org.aparoksha.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.fragment_updates.*
import org.aparoksha.app18.Notification
import org.aparoksha.app18.NotificationAdapter
import org.aparoksha.app18.R

/**
 * Created by sashank on 4/3/18.
 */

class UpdatesFragment :Fragment() {

    private lateinit var adapter : NotificationAdapter

    fun newInstance(): UpdatesFragment {

        val args = Bundle()
        args.putBoolean("isEvent", false)
        val fragment = UpdatesFragment()
        fragment.arguments = args
        return fragment
    }

    fun newInstance(eventID: Long): UpdatesFragment {

        val args = Bundle()
        args.putBoolean("isEvent", true)
        args.putLong("eventId", eventID)
        val fragment = UpdatesFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_updates, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isEventSpecific = arguments.getBoolean("isEvent")

        val ref = FirebaseDatabase.getInstance().getReference("verifiedNotifs")
        var query: Query = ref

        if (isEventSpecific) {
            val eventID = arguments.getLong("eventId")
            query = ref.orderByChild("eventID").equalTo(eventID.toString())
        }

        val options = FirebaseRecyclerOptions.Builder<Notification>()
                .setQuery(query, Notification::class.java)
                .build()

        adapter = NotificationAdapter(options, noNotifsTV)

        recyclerview.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerview.adapter = adapter

        noNotifsTV.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}