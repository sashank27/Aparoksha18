package org.aparoksha.app18.adapters

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.sponsor_container.view.*
import org.aparoksha.app18.GlideApp
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Sponsor

/**
 * Created by sashank on 11/3/18.
 */

class SponsorsAdapter(val context: Context) : RecyclerView.Adapter<SponsorsAdapter.ViewHolder>() {

    private var sponsorsList : List<Sponsor> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sponsor_container,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(sponsorsList[position])
    }

    override fun getItemCount() = sponsorsList.size

    fun updateSponsors(sponsors : List<Sponsor>) {
        sponsorsList = sponsors
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem(sponsor: Sponsor) {
            itemView.sponsorCategory.text = sponsor.categories.joinToString(",")
            itemView.sponsorName.text = sponsor.name
            GlideApp.with(context)
                    .load(sponsor.imageUrl)
                    .circleCrop()
                    .placeholder(R.drawable.logo)
                    .into(itemView.sponsorImage)

            itemView.sponsorLayout.setOnClickListener ({
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                Log.d("url",sponsor.website)
                if(sponsor.website.isNotEmpty())
                    customTabsIntent.launchUrl(context, Uri.parse(sponsor.website))
            })
        }
    }

}