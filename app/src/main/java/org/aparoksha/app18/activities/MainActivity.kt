package org.aparoksha.app18.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.aparoksha.app18.R
import org.aparoksha.app18.fragments.*

class MainActivity : AppCompatActivity(), FragNavController.RootFragmentListener {

    private lateinit var fragmentNavController: FragNavController
    private lateinit var fragmentControllerBuilder: FragNavController.Builder

    override fun getRootFragment(index: Int): Fragment {
        return when (index) {
            0 -> HomeFragment()
            1 -> TimelineFragment()
            2 -> EventsFragment()
            3 -> MapFragment()
            //3 -> UpdatesFragment().newInstance()
            4 -> InfoFragment()
            else -> {
                throw IllegalStateException("Index Invalid")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragmentManagement(savedInstanceState)
        initBottomNavigation()
    }


    private fun initFragmentManagement(savedInstanceState: Bundle?) {
        fragmentControllerBuilder = FragNavController.newBuilder(savedInstanceState, supportFragmentManager, R.id.containerFrame)
        fragmentControllerBuilder.rootFragmentListener(this, 5)
        fragmentNavController = fragmentControllerBuilder.build()
    }

    private fun initBottomNavigation() {

        val homeItem = AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp)
        val timelineItem = AHBottomNavigationItem("Timeline", R.drawable.ic_dashboard_black_24dp)
        val eventsItem = AHBottomNavigationItem("Events", R.drawable.ic_dashboard_black_24dp)
        val locationItem = AHBottomNavigationItem("Map", R.drawable.ic_location_on_black_24dp)
        val notificationItem = AHBottomNavigationItem("Notifications", R.drawable.ic_notifications_black_24dp)
        val infoItem = AHBottomNavigationItem("Info", R.drawable.ic_notifications_black_24dp)

        with(navigation) {
            addItem(homeItem)
            addItem(timelineItem)
            addItem(eventsItem)
            addItem(locationItem)
            //addItem(notificationItem)
            addItem(infoItem)

            accentColor = ContextCompat.getColor(this@MainActivity, R.color.colorAccent)
            defaultBackgroundColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)

            titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

            setOnTabSelectedListener { position, _ ->
                fragmentNavController.switchTab(position)
                return@setOnTabSelectedListener true
            }
        }
    }
}