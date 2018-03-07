package org.aparoksha.app18.ui

import android.support.v4.view.ViewPager
import android.view.View
import kotlinx.android.synthetic.main.fragment_flagship.view.*

/**
 * Created by sashank on 7/3/18.
 */

class ParallaxPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        when {
            position < -1 ->
                // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.alpha = 1f
            position <= 1 ->
                // [-1,1]
                page.flagshipImageView.translationX = -position * (page.width / 2) //Half the normal speed
            else ->
                // (1,+Infinity]
                // This page is way off-screen to the right.
                page.alpha = 1f
        }

    }

}