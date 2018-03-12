package org.aparoksha.app18

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

/**
 * Created by sashank on 8/3/18.
 */

class Aparoksha18 : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseMessaging.getInstance().subscribeToTopic("all")
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}