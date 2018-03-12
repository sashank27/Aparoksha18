package org.aparoksha.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.talk_item.*
import org.aparoksha.app18.GlideApp
import org.aparoksha.app18.R

/**
 * Created by akshat on 13/3/18.
 */

class TalkContainer: Fragment() {

    private val speakers = listOf<String>("Sahil Vaidya","Dr. Pawan Agarwal",
            "Varun Agarwal")

    private val time = listOf<String>("March 16","March 17","March18")

    private val images = listOf<Int>(R.drawable.talk_sahil,R.drawable.talk_pavan,R.drawable.talk_varun)

    private var position = 0

    companion object {
        fun newInstance(talkImage : Int) : TalkContainer {
            val fragment = TalkContainer()
            val args = Bundle()

            args.putInt("position", talkImage)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        position = arguments.getInt("position")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.talk_item,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        speaker.text = speakers[position]
        timeTech.text = time[position]

        GlideApp.with(context)
                .load(images[position])
                .into(talk_image)


    }
}