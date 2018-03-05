package org.aparoksha.app18.models

/**
 * Created by akshat on 5/3/18.
 */

data class Sponsor(
        var id: Long = 0,
        var name: String = "",
        var imageUrl: String = "",
        var categories: List<String> = emptyList(),
        var website: String = "",
        var priority: Int = -1
)