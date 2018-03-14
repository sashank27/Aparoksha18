package org.aparoksha.app18.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.notification_container.view.*
import org.aparoksha.app18.Notification
import org.aparoksha.app18.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sashank on 4/3/18.
 */

class NotificationAdapter(options: FirebaseRecyclerOptions<Notification>,
                          private val noNotifsTV : TextView)
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

            val sdf = SimpleDateFormat("MMM d, hh:mm a")
            sdf.timeZone = TimeZone.getTimeZone("Asia/India")

            itemView.timeTV.text = sdf.format(calendar.time)
        }
    }

    override fun getItem(position: Int): Notification {
        return super.getItem(itemCount - 1 - position)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        noNotifsTV.visibility = if (itemCount == 0) View.VISIBLE else View.GONE
    }
}