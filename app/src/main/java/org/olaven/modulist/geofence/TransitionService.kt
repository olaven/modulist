package org.olaven.modulist.geofence

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import org.olaven.modulist.App
import org.olaven.modulist.R
import org.olaven.modulist.activity.MainActivity
import kotlin.random.Random


class TransitionService: IntentService("Intent service for transitions") {


    override fun onHandleIntent(intent: Intent?) {

        val event = GeofencingEvent.fromIntent(intent)
        if (event.hasError()) {
            println("som error " + event.errorCode)
            return
        }

        val triggeringGeofences = event.triggeringGeofences

        triggeringGeofences.forEach {

            if (event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {

                sendNotification(it.requestId)
            }
        }
    }


    private fun sendNotification(message: String) {

        val notificationManager = NotificationManagerCompat.from(this)

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notification = NotificationCompat.Builder(this, App.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Modulist")
            .setAutoCancel(true)
            .setFullScreenIntent(pendingIntent, false)
            .setContentText(message)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}