package org.aparoksha.app18

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_updates.*

/**
 * Created by sashank on 4/3/18.
 */
class UpdatesFragment :Fragment() {

    private lateinit var adapter : NotificationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_updates, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mFirebaseDB = FirebaseDatabase.getInstance()
        val query = mFirebaseDB.getReference("notifications").orderByChild("timestamp")

        val options = FirebaseRecyclerOptions.Builder<Notification>()
                .setQuery(query, Notification::class.java)
                .build()

        adapter = NotificationAdapter(options)

        recyclerview.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerview.adapter = adapter
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