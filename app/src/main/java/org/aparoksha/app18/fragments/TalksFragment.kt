package org.aparoksha.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yarolegovich.discretescrollview.DSVOrientation
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_talks.*
import org.aparoksha.app18.R
import org.aparoksha.app18.adapters.TalksAdapter
import org.aparoksha.app18.models.Event
import org.aparoksha.app18.utils.AppDB

/**
 * Created by sashank on 6/3/18.
 */

class TalksFragment : Fragment(), DiscreteScrollView.OnItemChangedListener<TalksAdapter.TalksViewHolder> {

    private lateinit var infiniteAdapter : InfiniteScrollAdapter<TalksAdapter.TalksViewHolder>

    override fun onCurrentItemChanged(viewHolder: TalksAdapter.TalksViewHolder?, adapterPosition: Int) {
        val positionInDataSet = infiniteAdapter.getRealPosition(adapterPosition)
        //onItemChanged(data.get(positionInDataSet))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_talks, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDB  = AppDB.getInstance(context!!)
        val data : List<Event> = appDB.getAllEvents()
        createTalksView(data)

    }

    private fun createTalksView(data: List<Event>) {
        itemLayout.setOrientation(DSVOrientation.HORIZONTAL)
        itemLayout.addOnItemChangedListener(this)

        infiniteAdapter = InfiniteScrollAdapter.wrap(TalksAdapter(data,context!!))
        itemLayout.adapter = infiniteAdapter
        itemLayout.setOffscreenItems(3) //Reserve extra space equal to (childSize * count) on each side of the view
        itemLayout.setOverScrollEnabled(true) //Can also be set using android:overScrollMode xml attribute
        itemLayout.setItemTransitionTimeMillis(250)
        itemLayout.setItemTransformer(ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build())
    }
}