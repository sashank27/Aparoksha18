package org.aparoksha.app18.adapters

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.aparoksha.app18.models.Aparoksha
import org.aparoksha.app18.models.TimelineEvents
import org.aparoksha.app18.models.Timepoint
import org.aparoksha.app18.utils.ViewType

class TimelineRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>

    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        items = ArrayList()
        delegateAdapters.put(ViewType.HEADER, TimelineHeaderItemDelegateAdapter())
        delegateAdapters.put(ViewType.LINE, TimepointItemDelegateAdapter())
        delegateAdapters.put(ViewType.ITEM, TimelineItemDelegateAdapter())
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun addHeader(item: Aparoksha) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun addEvent(item: TimelineEvents) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun addTimepoint(item: Timepoint) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun reset() {
        this.items.removeAll(items)
    }
}
