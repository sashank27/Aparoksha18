package org.aparoksha.app18.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.aparoksha.app18.utils.ViewType

interface ViewTypeDelegateAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)

}
