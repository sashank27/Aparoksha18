package org.aparoksha.app18.models

import org.aparoksha.app18.utils.ViewType
import java.security.Timestamp

/**
 * Created by akshat on 5/3/18.
 */

data class TimelineEvents(val eventName: String,
                          val description: String,
                          val timestamp: Long,
                          val iconUrl: String,
                          val isLastItem: Boolean): ViewType {

    override fun getViewType(): Int = ViewType.ITEM
}