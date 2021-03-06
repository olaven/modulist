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
import org.olaven.modulist.database.entity.ModuleList


class CustomGeofence(val context: Context) {

    private val fences = mutableListOf<Geofence>()

    var geofencingClient = GeofencingClient(context)


    fun addFence(moduleList: ModuleList, placeName: String, latitude: Double, longitude: Double) {

        val id = "You are at $placeName. Do you want to pack ${moduleList.name}?"
        val radius = 300

        var geofence = Geofence.Builder()
            .setRequestId(id)
            .setCircularRegion(latitude, longitude, radius.toFloat())
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL)
            .setLoiteringDelay(0)
            .build()

        fences.add(geofence)

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
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getGeofencingRequest() = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL)
            .addGeofences(fences)
            .build()

    private fun checkPermission() = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}