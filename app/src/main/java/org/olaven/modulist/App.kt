package org.olaven.modulist

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.os.Build
import kotlin.random.Random

// paa startup
class App: Application() {

    companion object {
        val CHANNEL_NAME = "MODULIST_NOTIFICATION_CHANNEL"
        val CHANNEL_ID = "${CHANNEL_NAME}_ID"
        val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = Random.nextInt(65535)
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()
    }

    private fun createNotificationChannels() {

        // ta hoeyede for endringer i notifikasjoner i android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Main channel for modulist notificaions"

            val manager = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

    }
}