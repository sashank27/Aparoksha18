package org.aparoksha.app18.ui

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

/**
 * Created by sashank on 7/3/18.
 */

class CustomDurationScroller(context: Context, interpolator: Interpolator) : Scroller(context, interpolator) {

    private var scrollFactor = 1.0

    fun setScrollDurationFactor(scrollFactor: Double) {
        this.scrollFactor = scrollFactor
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, (duration * scrollFactor).toInt())
    }
}