package org.aparoksha.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_flagship.*
import org.aparoksha.app18.R
import org.aparoksha.app18.ui.GlideApp

/**
 * Created by sashank on 7/3/18.
 */

class FlagshipFragment : Fragment() {

    //private var imageUrl = ""
    private var image = 0

    companion object {
        fun newInstance(eventImage : Int) : FlagshipFragment {
            val fragment = FlagshipFragment()
            val args = Bundle()

            args.putInt("image", eventImage)
            //args.putString("url",event.imageUrl)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        image = arguments.getInt("image")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  =
            inflater.inflate(R.layout.fragment_flagship, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //flagshipTitle.text = title

        GlideApp.with(context)
                .load(image)
                .into(flagshipImageView)
    }
}