package org.aparoksha.app18.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.notification_container.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Notification
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sashank on 4/3/18.
 */

class NotificationAdapter(options: FirebaseRecyclerOptions<Notification>)
    : FirebaseRecyclerAdapter<Notification, NotificationAdapter.NotificationViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.notification_container, parent, false))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int, model: Notification) {
        holder.bindView(model)
    }

    class NotificationViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        fun bindView(notification: Notification) {

            itemView.titleTV.text = notification.title
            itemView.descriptionTV.text = notification.description

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
            calendar.timeInMillis = notification.timestamp.times(1000L)

            val sdf = SimpleDateFormat("hh:mm a")
            //sdf.timeZone = TimeZone.getTimeZone("Asia/India")

            val time = sdf.format(calendar.time)

            sdf.applyPattern("MMM d")
            itemView.timestampTV.text = notification.timestamp.toString()
            itemView.timestampTV.text = "$time ${sdf.format(calendar.time)}"
        }
    }
}