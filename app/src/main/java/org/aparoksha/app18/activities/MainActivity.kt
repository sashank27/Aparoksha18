package org.aparoksha.app18.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.aparoksha.app18.R
import org.aparoksha.app18.fragments.*
import java.util.*
import com.google.firebase.auth.FirebaseUser
import com.firebase.ui.auth.IdpResponse
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.database.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.aparoksha.app18.models.User
import com.google.firebase.database.DataSnapshot




class MainActivity : AppCompatActivity(), FragNavController.RootFragmentListener {

    private lateinit var fragmentNavController: FragNavController
    private lateinit var fragmentControllerBuilder: FragNavController.Builder
    private var RC_SIGN_IN = 123

    override fun getRootFragment(index: Int): Fragment {
        return when (index) {
            0 -> HomeFragment()
            1 -> EventContainerFragment()
            2 -> MapFragment()
            3 -> UpdatesFragment().newInstance()
            4 -> InfoFragment()

            else -> {
                throw IllegalStateException("Index Invalid")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(
                                    Arrays.asList<AuthUI.IdpConfig>(AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .build(),
                    RC_SIGN_IN)
        }

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

            accentColor = ContextCompat.getColor(this@MainActivity, R.color.colorAccent)
            defaultBackgroundColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)

            titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

            setOnTabSelectedListener { position, _ ->
                when (position) {
                    0 -> return@setOnTabSelectedListener true
                    1 -> startActivity<EventsActivity>()
                    2 -> startActivity<MapActivity>()
                    3 -> startActivity<UpdatesActivity>()
                    4 -> startActivity<InfoActivity>()
                }
                finish()
                //fragmentNavController.switchTab(position)
                return@setOnTabSelectedListener true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                toast("Signed In")
                val database = FirebaseDatabase.getInstance()
                val ref = database.getReference("/users")

                ref.addListenerForSingleValueEvent(object :ValueEventListener{

                    override fun onDataChange(p0: DataSnapshot) {
                        val users: MutableList<User?> = mutableListOf()
                        if(p0.value != null) {
                            for (snapshot in p0.getChildren()) {
                                users.add(snapshot.getValue(User::class.java))
                            }
                            var oldUser = false
                            val user = FirebaseAuth.getInstance().currentUser!!
                            users.forEach {
                                if (it != null) {
                                    if (it.email.equals(user.email)) oldUser = true
                                }
                            }

                            if (!oldUser) {
                                val mFirebaseDatabase = FirebaseDatabase.getInstance()
                                val databaseReference = mFirebaseDatabase.getReference("users")
                                val sharedPrefs = getSharedPreferences("ApkPrefs", Context.MODE_PRIVATE)
                                databaseReference.push().setValue(User("", user.email!!, ""))
                            }
                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        println("The read failed: " + p0.code)
                    }

                })
            } else {
                toast("Please Sign In to use the app")
                finish()
            }
        }
    }
}