package org.aparoksha.app18.models

/**
 * Created by sashank on 4/3/18.
 */
data class Notification(
        val description: String = "",
        val senderName: String = "",
        val timestamp: Long = 0,
        val title: String = ""
)