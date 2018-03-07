package org.aparoksha.app18

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.aparoksha.app18.fragments.EventsFragment
import org.aparoksha.app18.fragments.HomeFragment
import org.aparoksha.app18.fragments.TimelineFragment
import org.aparoksha.app18.fragments.UpdatesFragment

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_schedule -> {
                switchFragment(TimelineFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                switchFragment(UpdatesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_events -> {
                switchFragment(EventsFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.containerFrame, HomeFragment())
                .commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.containerFrame,  fragment)
                .commit()
    }
}