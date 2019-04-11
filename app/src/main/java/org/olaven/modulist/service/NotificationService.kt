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

        // hent ut geofence-info fra et intent
        val fenceEvent = GeofencingEvent.fromIntent(intent)

        println("Fence: ${fenceEvent.geofenceTransition}")

        if (fenceEvent.hasError()) {
            println(fenceEvent.errorCode.toString())
            return
        }

        // Check if I am interested in event
        if (fenceEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ) {

            // The triggered fences (NOTE: These should have an ID that matches the map)
            val triggeringGeofences = fenceEvent.triggeringGeofences

            // Send notification and log the transition details.
            sendNotification("Some geofence triggered - ${fenceEvent.geofenceTransition}, $triggeringGeofences")

            fenceEvent.triggeringGeofences.forEach {
                print(it.requestId)
            }
        }


        //TODO: REMOVE
        //NOTE: JUST TESTING
        if (fenceEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {

            // The triggered fences (NOTE: These should have an ID that matches the map)
            val triggeringGeofences = fenceEvent.triggeringGeofences

            // Send notification and log the transition details.
            sendNotification("Some geofence triggered - ${fenceEvent.geofenceTransition}, $triggeringGeofences")

            fenceEvent.triggeringGeofences.forEach {
                print(it.requestId)
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
        notificationManager.notify(Random.nextInt(), notification)
    }
}