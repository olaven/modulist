package org.olaven.modulist

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.os.Build
import kotlin.random.Random

// paa startup
class App: Application() {

    // App constants:
    companion object {
        val CHANNEL_NAME = "MODULIST_NOTIFICATION_CHANNEL"
        val CHANNEL_ID = "${CHANNEL_NAME}_ID"

        val REQUEST_ACCESS_FINE_LOCATION = Random.nextInt(65535)
        val REQUEST_ACCESS_CAMERA = Random.nextInt(65535)
        val REQUEST_CODE_PLACES_ADRESS = Random.nextInt(65535)
        val REQUEST_CODE_PLACES_CITIES = Random.nextInt(65535)

        // NOTE: The key is restricted with Google Developer Console -> Therefore, It should be relatively secure
        val API_PLACES_KEY = "AIzaSyCCASGI3A36kyHcqE225EeF3RmUcHPd1bg"
        // Ideally, this should not be shared. However, no billing information is attached to this account and it is a school project.
        // It may pass for those reasons. I almost all circumstances, this should not be the case.
        val API_WEATHERBIT_KEY = "549fbc9a62a8424baab29a6fce91eb57"
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