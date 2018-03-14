package org.aparoksha.app18.activities

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import com.richpathanimator.RichPathAnimator
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val spaceshipPath = richPathView.findRichPathByName("spaceship")
        val exhaustPath = richPathView.findRichPathByName("spaceship_exhaust")
        val aparokshaTextPath = richPathView.findRichPathByName("aparoksha_text")


        RichPathAnimator.animate(aparokshaTextPath)
                .fillColor(Color.parseColor("#fff082f4"),
                        Color.parseColor("#ffb2ff82"),
                        Color.parseColor("#ff38dbff"))
                .duration(2000)
                .interpolator(AccelerateDecelerateInterpolator())
                .andAnimate(spaceshipPath)
                .translationX(-100f, 0f)
                .translationY(100f, 0f)
                .duration(1500)
                .startDelay(100)
                .interpolator(AccelerateInterpolator())
                .start()

        //AppDB.getInstance(this)
/*
        RichPathAnimator.animate(spaceshipPath)
                .translationX(-100f, 50f)
                .translationY(100f, -50f)
                .duration(1500)
                .start()*/


        Handler().postDelayed({
            startActivity<MainActivity>()
            finish()
        }, 2500)
    }

}
