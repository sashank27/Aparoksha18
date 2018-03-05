package org.aparoksha.app18.models

import org.aparoksha.app18.utils.ViewType

data class Timepoint(val timepoint: String) : ViewType {

    override fun getViewType(): Int = ViewType.LINE

}
