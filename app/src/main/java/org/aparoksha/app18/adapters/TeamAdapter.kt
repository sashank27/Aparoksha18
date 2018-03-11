package org.aparoksha.app18.adapters

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.team_member_container.view.*
import org.aparoksha.app18.R
import org.aparoksha.app18.models.Person
import org.aparoksha.app18.GlideApp
import org.aparoksha.app18.models.Event


/**
 * Created by akshat on 9/3/18.
 */

class TeamAdapter(val context: Context) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    var teamList : List<Person> = listOf()

    override fun getItemCount() = teamList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.team_member_container, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, teamList[position])
    }

    fun updateTeam(team: List<Person>) {
        teamList = team
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var callNumber: String
        val requestCode = 123

        fun bindItem(context: Context, person: Person) {
            itemView.nameTextView.text = person.name
            itemView.positionTextView.text = person.position
            GlideApp.with(context)
                    .load(person.imageUrl)
                    .circleCrop()
                    .placeholder(R.drawable.logo)
                    .into(itemView.personImageView)

            itemView.floatingActionButton.setOnClickListener ({
                callNumber = person.contact
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context as Activity,
                            arrayOf(Manifest.permission.CALL_PHONE),
                            requestCode)
                }
                else
                    context.startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:$callNumber")))
            })
        }
    }
}