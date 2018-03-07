package org.aparoksha.app18

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by sashank on 8/3/18.
 */

class Aparoksha18 : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}