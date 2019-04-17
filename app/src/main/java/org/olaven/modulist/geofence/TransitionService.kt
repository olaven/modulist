package org.olaven.modulist.geofence

import android.app.IntentService
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import org.olaven.modulist.App
import org.olaven.modulist.R
import kotlin.random.Random

class TransitionService: IntentService("TransitionsService") {



    override fun onHandleIntent(intent: Intent?) {

        val geofenceTransition = GeofencingEvent.fromIntent(intent).geofenceTransition

        when (geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                // do something
                sendNotification("You entered the area")
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                // do something else
                sendNotification("you exited the area")
            }
            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                // do something else again
                sendNotification("You dwelled in the area")
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