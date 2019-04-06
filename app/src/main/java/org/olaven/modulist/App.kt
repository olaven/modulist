package org.olaven.modulist

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import org.olaven.modulist.service.LocationNotificationService

// Kode som kjoerer naar appen kjoeres
class App: Application() {



    companion object {
        val CHANNEL_NAME = "MODULIST_NOTIFICATION_CHANNEL"
        val CHANNEL_ID = "${CHANNEL_NAME}_ID"
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()
        startLocationService()
    }

    private fun startLocationService() {

        val intent = Intent(this, LocationNotificationService::class.java)
        startService(intent)
    }

    private fun createNotificationChannels() {


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