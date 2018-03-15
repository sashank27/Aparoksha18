package org.aparoksha.app18.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.talk_item.view.*
import org.aparoksha.app18.GlideApp
import org.aparoksha.app18.R

/**
 * Created by akshat on 13/3/18.
 */

class TalksViewAdapter(val context: Context) : RecyclerView.Adapter<TalksViewAdapter.TalksViewHolder>() {

    private val talksName = arrayOf("Sahil Vaidya", "Dr. Pawan Agarwal")
    private val talksImage = arrayOf(R.drawable.talk_sahil, R.drawable.talk_pavan)
    private val talksTime = arrayOf("16th October", "17th October")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.talk_item,parent,false)
        return TalksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TalksViewHolder, position: Int) {
        holder.bindItem(talksName[position], talksImage[position], talksTime[position])
    }

    override fun getItemCount() = talksName.size

    inner class TalksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItem(name: String, drawable: Int,time: String) {
            itemView.speakerName.text = name
            itemView.timeTalk.text = time

            GlideApp.with(context)
                    .load(drawable)
                    .into(itemView.talkImage)
        }
    }

}