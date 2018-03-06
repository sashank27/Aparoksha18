package org.aparoksha.app18.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import com.esotericsoftware.minlog.Log.debug
import org.jetbrains.anko.debug

/**
 * Created by akshat on 6/3/18.
 */
fun isNetworkConnectionAvailable(activity: Activity): Boolean {
    val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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

fun showAlert(activity: Activity) {
    val builder = AlertDialog.Builder(activity)
    builder.setTitle("No internet Connection")
    builder.setMessage("Please turn on internet connection to continue. Internet is needed at least once before running the app.")
    builder.setNegativeButton("close") { _, _ -> activity.finish() }
    val alertDialog = builder.create()
    alertDialog.show()
}
