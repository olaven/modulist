package org.olaven.modulist.service

import android.Manifest
import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import org.olaven.modulist.R
import org.olaven.modulist.randomString


//"An IntentService can post a notification, do long-running background work, send intents to other services, or send a broadcast intent"
class GeofenceService: IntentService("Location-notification") {

    private lateinit var geofencingClient: GeofencingClient
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, NotificationService::class.java)
        PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    override fun onCreate() {
        super.onCreate()

        geofencingClient = GeofencingClient(this)
    }




    // TODO: sende intent fra "client"/GUI -> haandteres her?
    override fun onHandleIntent(intent: Intent?) {

        val lat = intent?.extras?.getDouble(getString(R.string.add_fence_lat_key))
        val long = intent?.extras?.getDouble(getString(R.string.add_fence_long_key))


        if (lat != null && long != null) {

            addFence(lat, long)
        }
    }

    private fun addFence(lat: Double, long: Double) {

        // NOTE: although just getting a random chance of duplciates,
        // it is extremely unlikey at this scale
        val id = randomString(20)
        val geofence = Geofence.Builder()
            .setRequestId(id)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL) // ONLY FOR TESTING //TODO: REMOVE THIS LINE
            .setLoiteringDelay(0) // ONLY FOR TESTING //TODO: REMOVE THIS LINE
            .setCircularRegion(lat, long, 200f)
            .setExpirationDuration(300000)
            .build()

        if (checkPermission()) {

            geofencingClient.addGeofences(getGeofencingRequest(geofence), geofencePendingIntent)?.run {
                addOnSuccessListener {

                    println("updated geofences")
                }
                addOnFailureListener {

                    println("failed to set geofence")
                }
            }
        }

    }

    private fun getGeofencingRequest(geofence: Geofence) =
        GeofencingRequest.Builder().apply {
            //setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL) // JUST FOR TESTING
            addGeofence(geofence)
        }.build()


    private fun checkPermission() = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}
