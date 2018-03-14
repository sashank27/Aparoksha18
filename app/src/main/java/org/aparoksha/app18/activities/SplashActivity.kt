package org.aparoksha.app18.activities

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_splash.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.aparoksha.app18.utils.AppDB
import org.aparoksha.app18.R
import org.aparoksha.app18.models.*
import org.aparoksha.app18.utils.showAlert
import org.jetbrains.anko.*

class SplashActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    private fun fetchLatestData() {
        doAsync {
            val appDB = AppDB.getInstance(this@SplashActivity)
            val client = OkHttpClient()

            try {
                val request = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/events.json")
                        .build()


                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val list = Moshi.Builder()
                            .build()
                            .adapter<Array<Event>>(Array<Event>::class.java)
                            .fromJson(response.body()?.string())

                    appDB.storeEvents(events = list.toList())
                }

                val request2 = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/sponsors.json")
                        .build()
                val response2 = client.newCall(request2).execute()
                if (response2.isSuccessful) {
                    val arrayOfSponsors = Moshi.Builder().build()
                            .adapter<Array<Sponsor>>(Array<Sponsor>::class.java)
                            .fromJson(response2.body()?.string())
                    appDB.storeSponsors(arrayOfSponsors.toList())
                }

                val request3 = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/team.json")
                        .build()
                val response3 = client.newCall(request3).execute()

                if (response3.isSuccessful) {
                    val teamArray = Moshi.Builder().build()
                            .adapter<Array<Person>>(Array<Person>::class.java)
                            .fromJson(response3.body()?.string())

                    appDB.storeTeam(teamArray.toList())
                }

                val request4 = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/developer.json")
                        .build()
                val response4 = client.newCall(request4).execute()

                if(response4.isSuccessful) {
                    val developersArray = Moshi.Builder().build()
                            .adapter<Array<Developer>>(Array<Developer>::class.java)
                            .fromJson(response4.body()?.string())

                    appDB.storeDevelopers(developersArray.toList())
                }

                val request5 = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/proshows.json")
                        .build()
                val response5 = client.newCall(request5).execute()

                if(response5.isSuccessful) {
                    val flagshipsArray = Moshi.Builder().build()
                            .adapter<Array<FlagshipEvents>>(Array<FlagshipEvents>::class.java)
                            .fromJson(response5.body()?.string())

                    appDB.storeFlagships(flagshipsArray.toList())
                }

                uiThread {
                    if (!response.isSuccessful || !response2.isSuccessful || !response3.isSuccessful || !response4.isSuccessful || !response5.isSuccessful) {
                        if (sharedPrefs.getBoolean("firstrun", true)) {
                            showAlert(this@SplashActivity)
                        } else {
                            showFinishedAnimation()
                        }
                    } else {
                        showFinishedAnimation()
                    }
                }
            } catch (exception: Exception) {
                uiThread {
                    error(exception)
                    //animationView.cancelAnimation()
                    if (sharedPrefs.getBoolean("firstrun", true)) {
                        showAlert(this@SplashActivity)
                    } else {
                        showFinishedAnimation()
                    }
                }
            }
        }
    }

    private fun showFinishedAnimation() {
        /*animationView.setAnimation("checked_done.json")
        animationView.loop(false)
        animationView.playAnimation()
        animationView.addAnimatorListener(AnimatorListenerAdapter(
                onStart = { },
                onEnd = {
                    sharedPrefs.edit().putBoolean("firstrun", false).commit()
                    startActivity<MainActivity>()
                    finish()
                },
                onCancel = { },
                onRepeat = { }))*/
        toast("Data fetched successfully")
        sharedPrefs.edit().putBoolean("firstrun", false).commit()
        startActivity<MainActivity>()
        finish()
    }

    private fun isNetworkConnectionAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return if (isConnected) {
            debug("Network Connected")
            true
        } else {
            debug("Network not Connected")
            false
        }
    }

}
