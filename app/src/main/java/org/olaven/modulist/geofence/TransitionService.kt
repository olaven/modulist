package org.olaven.modulist.geofence

import android.app.IntentService
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import org.olaven.modulist.App
import org.olaven.modulist.R
import java.lang.Exception
import kotlin.random.Random

class TransitionService: IntentService("Intent service for transitions") {


    override fun onCreate() {
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {

        val event = GeofencingEvent.fromIntent(intent)
        if (event.hasError()) {
            println("som error " + event.errorCode)
            return
        }

        val triggeringGeofences = event.triggeringGeofences

        triggeringGeofences.forEach {
            print(it.toString() + " - " + it.requestId)

            if (event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {

                sendNotification(it.requestId)
            }
        }
    }


    private fun sendNotification(message: String) {

        val notificationManager = NotificationManagerCompat.from(this)

        val notification = NotificationCompat.Builder(this, App.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notification content title")
            .setContentText(message)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}