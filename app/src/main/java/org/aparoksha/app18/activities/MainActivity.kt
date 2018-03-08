package org.aparoksha.app18.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.aparoksha.app18.R
import org.aparoksha.app18.fragments.EventsFragment
import org.aparoksha.app18.fragments.HomeFragment
import org.aparoksha.app18.fragments.TimelineFragment
import org.aparoksha.app18.fragments.UpdatesFragment

class MainActivity : AppCompatActivity(), FragNavController.RootFragmentListener {

    private lateinit var fragmentNavController: FragNavController
    private lateinit var fragmentControllerBuilder: FragNavController.Builder

    override fun getRootFragment(index: Int): Fragment {
        return when (index) {
            0 -> HomeFragment()
            1 -> TimelineFragment()
            2 -> EventsFragment()
            3 -> UpdatesFragment()
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
        fragmentControllerBuilder.rootFragmentListener(this, 4)
        fragmentNavController = fragmentControllerBuilder.build()
    }

    private fun initBottomNavigation() {

        val homeItem = AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp)
        val timelineItem = AHBottomNavigationItem("Timeline", R.drawable.ic_dashboard_black_24dp)
        val eventsItem = AHBottomNavigationItem("Events", R.drawable.ic_dashboard_black_24dp)
        val notificationItem = AHBottomNavigationItem("Notifications", R.drawable.ic_notifications_black_24dp)

        with(navigation) {
            addItem(homeItem)
            addItem(timelineItem)
            addItem(eventsItem)
            addItem(notificationItem)

            accentColor = android.graphics.Color.parseColor("#FF0000")
            defaultBackgroundColor = android.graphics.Color.parseColor("#242038")

            titleState = com.aurelhubert.ahbottomnavigation.AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE

            setOnTabSelectedListener { position, _ ->
                if (position < 4) {
                    fragmentNavController.switchTab(position)
                }
                kotlin.io.println(position)
                return@setOnTabSelectedListener true
            }
        }
    }
}