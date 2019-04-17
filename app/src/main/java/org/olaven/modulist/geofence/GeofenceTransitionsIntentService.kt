package org.olaven.modulist.service

import android.app.IntentService
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceTransitionsIntentService: IntentService() {

    override fun onHandleIntent(intent: Intent?) {

        val geofenceTransition = GeofencingEvent.fromIntent(intent).geofenceTransition

        when (geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                // do something
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                // do something else
            }
            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                // do something else again
            }
        }
    }
}