package org.aparoksha.app18.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import org.aparoksha.app18.R

import kotlinx.android.synthetic.main.activity_info.*
import org.jetbrains.anko.startActivity

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {

        val homeItem = AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp)
        val timelineItem = AHBottomNavigationItem("Events", R.drawable.ic_events_24dp)
        val eventsItem = AHBottomNavigationItem("Map", R.drawable.ic_location_on_black_24dp)
        val locationItem = AHBottomNavigationItem("Updates", R.drawable.ic_notifications_black_24dp)
        val infoItem = AHBottomNavigationItem("Info", R.drawable.ic_info_black_24dp)

        with(navigation) {
            addItem(homeItem)
            addItem(timelineItem)
            addItem(eventsItem)
            addItem(locationItem)
            addItem(infoItem)

            navigation.currentItem = 4
            accentColor = ContextCompat.getColor(this@InfoActivity, R.color.colorAccent)
            defaultBackgroundColor = ContextCompat.getColor(this@InfoActivity, R.color.colorPrimary)

            titleState = com.aurelhubert.ahbottomnavigation.AHBottomNavigation.TitleState.ALWAYS_SHOW

            setOnTabSelectedListener { position, _ ->
                when (position) {
                    0 -> startActivity<MainActivity>()
                    1 -> startActivity<EventsActivity>()
                    2 -> startActivity<MapActivity>()
                    3 -> startActivity<UpdatesActivity>()
                    4 -> return@setOnTabSelectedListener true
                }
                finish()
                return@setOnTabSelectedListener true
            }
        }
    }

}
