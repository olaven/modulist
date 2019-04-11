package org.olaven.modulist.service

import android.Manifest
import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import java.util.*


//"An IntentService can post a notification, do long-running background work, send intents to other services, or send a broadcast intent"
class GeofenceService: IntentService("Location-notification") {

    private val fences = HashMap<String, Geofence>().also {
        it["test_fence"] = Geofence.Builder()
            .setRequestId("test_fence")
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL) // ONLY FOR TESTING 
            .setLoiteringDelay(10) // ONLY FOR TESTING
            .setCircularRegion(59.916044, 10.760100, 200f)
            .setExpirationDuration(29999)
            .build()

        //NOTE: 59.916044, 10.760100 -> SKOLE
    }

    private lateinit var geofencingClient: GeofencingClient
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceService::class.java)
        PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    override fun onCreate() {
        super.onCreate()

        //TODO: service starts all the time
        geofencingClient = GeofencingClient(this)
        updateFences()
    }



    // TODO: sende intent fra "client"/GUI -> haandteres her?
    override fun onHandleIntent(intent: Intent?) {

        println("Intent: ${intent?.action}")
        //TODO: Check if intent was about adding fence!
        if (true) {

            updateFences()
        }
    }

    private fun updateFences() {

        if (checkPermission()) {

            geofencingClient.addGeofences(getGeofencingRequest(), geofencePendingIntent)?.run {
                addOnSuccessListener {

                    println("added geofence")
                }
                addOnFailureListener {
                    // Failed to add geofences
                    // ...
                    println("failed to set geofence")
                }
            }
        }
    }



    private fun getGeofencingRequest() =
        GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(fences.values.toList())
        }.build()



    private fun checkPermission() = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}
