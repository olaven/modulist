package org.olaven.modulist.service

import android.app.IntentService
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import org.olaven.modulist.App
import org.olaven.modulist.R
import kotlin.random.Random

/**
 * Will catch the event from GeofenceService
 */
class NotificationService: IntentService("notificaion_service") {


    override fun onHandleIntent(intent: Intent?) {


        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        if (geofencingEvent.hasError()) {
            val errorMessage = geofencingEvent.errorCode.toString()
            println(errorMessage)
            return
        }

        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            val triggeringGeofences = geofencingEvent.triggeringGeofences


            // Send notification and log the transition details.
            sendNotification("Some geofence triggered - $geofenceTransition, $triggeringGeofences")
        } else {
            // Log the error.
            println("invalid transition type")
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
        notificationManager.notify(Random.nextInt(), notification)
    }
}