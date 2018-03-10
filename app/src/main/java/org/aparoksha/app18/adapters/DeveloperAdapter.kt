package org.aparoksha.app18.adapters

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.developer_container.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Developer
import org.aparoksha.app18.ui.GlideApp


/**
 * Created by akshat on 9/3/18.
 */

class DeveloperAdapter(val context: Context) : RecyclerView.Adapter<DeveloperAdapter.ViewHolder>() {

    private val developerList = ArrayList<Developer>()

    override fun getItemCount() = developerList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.developer_container, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, developerList[position])
    }

    fun addDeveloper(developerList: List<Developer>) {
        this.developerList.addAll(developerList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(context: Context, developer: Developer) {
            itemView.nameTextView.text = developer.name
            itemView.positionTextView.text = developer.position
            GlideApp.with(context)
                    .load(developer.imageUrl)
                    .circleCrop()
                    .placeholder(R.drawable.logo)
                    .into(itemView.personImageView)
          itemView.floatingActionButton.setOnClickListener {
                openChromeTab(context,developer.gitHubLink)
          }
        }
       
        fun openChromeTab(context: Context,url: String){
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        }
    }
}