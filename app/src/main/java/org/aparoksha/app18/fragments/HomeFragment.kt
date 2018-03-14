package org.aparoksha.app18.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.content_home_fragment.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.aparoksha.app18.R
import org.aparoksha.app18.adapters.FlagshipViewPagerAdapter
import org.aparoksha.app18.ui.ParallaxPageTransformer
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.aparoksha.app18.models.User


/**
 * Created by sashank on 3/3/18.
 */

class HomeFragment : Fragment() {

    private val flagshipData = arrayOf(R.drawable.hint, R.drawable.fragfest, R.drawable.codered,
            R.drawable.greyhound, R.drawable.topbot, R.drawable.bootroot, R.drawable.humblefool)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch(UI) {
            val adapter = FlagshipViewPagerAdapter(childFragmentManager, flagshipData)
            val sharedPrefs = activity.getSharedPreferences("ApkPrefs", Context.MODE_PRIVATE)
            val key = sharedPrefs.getString("key","aa")

            viewPager.adapter = adapter
            viewPager.setPageTransformer(true, ParallaxPageTransformer())
            viewPager.startAutoScroll(1000)
            viewPager.setAutoScrollDurationFactor(15.0)

            signOutBtn.setOnClickListener {
                AuthUI.getInstance().signOut(activity)
                activity.finish()
            }

            val mFirebaseDatabase = FirebaseDatabase.getInstance()
            val userRef = mFirebaseDatabase.getReference("users/"+key)

            val userListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue<User>(User::class.java)

                    if (user != null) {
                        if(userUidTV != null)
                            userUidTV.text = user.id
                        val text = user.id
                        if(!text.equals("")) {
                            val data = "{\n" +
                                    "\t\"id\":" + user.id + ",\n" +
                                    "\t\"name\":" + user.name + ",\n" +
                                    "\t\"email\":" + user.email +"\n" +
                                    "}"
                            val multiFormatWriter = MultiFormatWriter()

                            try {
                                val bitmap = BarcodeEncoder()
                                        .createBitmap(multiFormatWriter
                                                .encode(data, BarcodeFormat.QR_CODE, 300, 300))
                                userQRcode.setImageBitmap(bitmap)
                            } catch (e: WriterException) {
                                e.printStackTrace()
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("error", "loadPost:onCancelled", databaseError.toException())
                }
            }
            userRef.addValueEventListener(userListener)
        }
    }

}
