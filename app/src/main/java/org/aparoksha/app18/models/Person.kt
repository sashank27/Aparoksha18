package org.aparoksha.app18.models

/**
 * Created by akshat on 5/3/18.
 */

data class Person(
        val id: Long = 0,
        val name: String = "",
        val imageUrl: String = "",
        val position: String = "",
        val contact: String = ""
)