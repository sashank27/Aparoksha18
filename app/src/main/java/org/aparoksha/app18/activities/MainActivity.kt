package org.aparoksha.app18.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_activity_home.*
import org.aparoksha.app18.GlideApp
import org.aparoksha.app18.R
import org.aparoksha.app18.adapters.FlagshipViewPagerAdapter
import org.aparoksha.app18.models.User
import org.aparoksha.app18.ui.ParallaxPageTransformer
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*


class MainActivity : AppCompatActivity() {

    private var RC_SIGN_IN = 123
    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pd = ProgressDialog(this, R.style.AppCompatAlertDialogStyle)
        updateUserData()

        val flagshipData = arrayOf(R.drawable.hint, R.drawable.fragfest, R.drawable.codered,
                R.drawable.greyhound, R.drawable.topbot, R.drawable.bootroot, R.drawable.humblefool)

        val adapter = FlagshipViewPagerAdapter(supportFragmentManager, flagshipData)

        autoViewPager.apply {
            this.adapter = adapter
            setPageTransformer(true, ParallaxPageTransformer())
            startAutoScroll(1000)
            setAutoScrollDurationFactor(15.0)
        }

        signOutBtn.setOnClickListener {
            AuthUI.getInstance().signOut(this@MainActivity)
            updateUserData()
        }

        signInButton.setOnClickListener {
            pd.setTitle("Signing In")
            pd.setMessage("Loading...")
            pd.show()
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(
                                    Arrays.asList<AuthUI.IdpConfig>(
                                            AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .build(),
                    RC_SIGN_IN)
        }

        initBottomNavigation()
    }

    private fun updateUserData() {

        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {

            signInLayout.visibility = View.VISIBLE
            userDetailView.visibility = View.GONE

        } else {

            signInLayout.visibility = View.GONE
            userDetailView.visibility = View.VISIBLE

            val uid = user.uid
            val userRef = FirebaseDatabase.getInstance().getReference("users/$uid")
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    error(p0.message)
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.value != null) {
                        pd.dismiss()
                        val userData = dataSnapshot.getValue<User>(User::class.java)!!
                        apkIDTextView.text = userData.id
                        emailTextView.text = userData.email
                        nameTextView.text = userData.name

                        GlideApp.with(this@MainActivity)
                                .load(user.photoUrl)
                                .placeholder(R.drawable.logo)
                                .into(userImageView)

                        val text = userData.id
                        val multiFormatWriter = MultiFormatWriter()

                        if (text.isNotEmpty()) {
                            try {
                                val bitmap = BarcodeEncoder()
                                        .createBitmap(multiFormatWriter
                                                .encode(text, BarcodeFormat.QR_CODE, 300, 300))
                                userQRcode.setImageBitmap(bitmap)
                            } catch (e: WriterException) {
                                e.printStackTrace()
                            }
                        }
                    }
                }

            })
        }
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
                val currentUser = FirebaseAuth.getInstance().currentUser!!
                val database = FirebaseDatabase.getInstance()
                val ref = database.getReference("/users").child(currentUser.uid)

                ref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.value == null) {
                            ref.setValue(User(
                                    name = currentUser.displayName!!,
                                    id = "",
                                    email = currentUser.email!!
                            ))
                        } else {
                            updateUserData()
                        }
                    }
                })

            }
        }
    }
}
