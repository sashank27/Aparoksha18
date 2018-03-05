package org.aparoksha.app18.models

import org.aparoksha.app18.utils.ViewType

/**
 * Created by akshat on 5/3/18.
 */

data class Aparoksha(val title: String,
                     val description: String,
                     val dates: String,
                     val college: String): ViewType {

    override fun getViewType(): Int = ViewType.HEADER
}