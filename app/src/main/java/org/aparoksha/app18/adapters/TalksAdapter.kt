package org.aparoksha.app18.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_talks.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Event

/**
 * Created by sashank on 6/3/18.
 */

class TalksAdapter(private val data: List<Event>, val context: Context) : RecyclerView.Adapter<TalksAdapter.TalksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalksViewHolder {
        return TalksAdapter.TalksViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_talks, parent, false))
    }

    override fun onBindViewHolder(holder: TalksViewHolder, position: Int) {
        holder.bindView(context, data[position])
    }

    override fun getItemCount(): Int = data.size

    class TalksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(context: Context, event: Event) {
            Picasso.with(context)
                    .load(event.imageUrl)
                    .into(itemView.imageSpeaker)

            Log.d("Values",event.name + " " + event.imageUrl)

            itemView.nameSpeaker.text = event.name
        }
    }
}
