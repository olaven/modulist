package org.olaven.modulist.service

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import org.olaven.modulist.App
import org.olaven.modulist.R
import kotlin.random.Random


class LocationNotificationService : Service() {

    override fun onBind(intent: Intent): IBinder? {

        return null
    }

    override fun onCreate() {

        sendNotification("Service has started")

        if (checkPermission(applicationContext)) {
            sendNotification("permissions granted for location")
        } else {
            sendNotification("permissions NOT granted")
        }
    }

    private fun sendNotification(message: String) {

        val notificationManager = NotificationManagerCompat.from(this)

        val notification = NotificationCompat.Builder(this, App.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Some tititle").setContentText(message)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }


    //NOTE: notifications are not classified as dangerous. Therefore, it is not requried to check for it.
    // However, location is PROTECTION_NORMAL
    fun checkPermission(context: Context): Boolean = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
}
