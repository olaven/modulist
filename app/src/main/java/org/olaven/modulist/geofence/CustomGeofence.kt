package org.olaven.modulist.geofence

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest


// TODO: Clean this up and get it neat and tidy!
class CustomGeofence(val context: Context) {

    private val fences = HashMap<String, Geofence>()

    var geofencingClient = GeofencingClient(context)
    private var mGeofencePendingIntent: PendingIntent = getGeofencePendingIntent()


    fun addFence(name: String, latitude: Double, longitude: Double) {

        val radius = 300

        var geofence = Geofence.Builder()
            .setRequestId(name)
            .setCircularRegion(latitude, longitude, radius.toFloat())
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER or
                        Geofence.GEOFENCE_TRANSITION_EXIT or
                        Geofence.GEOFENCE_TRANSITION_DWELL
            )
            .setLoiteringDelay(1)
            .build()

        fences[name] = geofence

        if (checkPermission())
            geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                .addOnSuccessListener {
                    println("added geofence")
                }
                .addOnFailureListener {
                    println("failed to add geofence")
                }
    }

    private fun getGeofencePendingIntent(): PendingIntent {
        val intent = Intent(context, TransitionService::class.java)
        mGeofencePendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        return mGeofencePendingIntent
    }

    private fun getGeofencingRequest(): GeofencingRequest {
        return GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL)
        .addGeofences(fences.values.toList())
            .build()
    }

    private fun checkPermission() = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}