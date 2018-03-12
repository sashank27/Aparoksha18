package org.aparoksha.app18

/**
 * Created by sashank on 4/3/18.
 */
data class Notification(
        val description: String = "",
        val eventID : Long = 0,
        val senderName : String = "",
        val timestamp : Long = 0,
        val title : String = "",
        val verified : Boolean = false
)