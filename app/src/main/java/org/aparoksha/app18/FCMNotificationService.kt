package org.aparoksha.app18

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import org.jetbrains.anko.notificationManager
import java.lang.Math.random


/**
 * Created by betterclever on 16/2/18.
 */

class FCMNotificationService : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_MAIN = "Main"
        const val GROUP_NEW_REPORTS = "New Reports"
    }

    data class ObservationNotificationData(
            val title: String = "",
            val urgent: String = ""
    )

    private fun showNewReportNotification(data: Map<String, String>): Notification {

        val reportTitle = data["name"]
        val senderInfoString = data["sender"]
        val observationString = data["observations"]

        val observations = Gson().fromJson(observationString, Array<ObservationNotificationData>::class.java)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.unseen)

        val builder = NotificationCompat.Builder(this, CHANNEL_MAIN)
                .setContentTitle("hv")
                .setSmallIcon(R.drawable.ic_mail_black_24dp)
                .setGroup(GROUP_NEW_REPORTS)
                .setContentText("hey")


        return builder.build()
    }

    private fun showReportMarkCompiledNotification(messageData: Map<String, String>): Notification {
        val builder = NotificationCompat.Builder(this, CHANNEL_MAIN)
                .setContentTitle("Report Complied")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentText("")
        return builder.build()
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Log.d("akshat",p0.data.toString())

        val messageData = p0.data

        val notification = when (messageData["type"]) {
            "New Report" -> showNewReportNotification(messageData)
            "Report marked complied" -> showReportMarkCompiledNotification(messageData)
            else -> null
        }

        notification?.let {
            val id = (random() * 10000).toInt()

            val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel("Main",
                        "Reports",
                        NotificationManager.IMPORTANCE_DEFAULT)
            } else null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(id, it)
        }

    }


}