package org.olaven.modulist.service

import android.Manifest
import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.TaskStackBuilder
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.GeofencingRequest
import org.olaven.modulist.App
import org.olaven.modulist.R
import org.olaven.modulist.activity.MainActivity
import java.util.*
import kotlin.random.Random


//"An IntentService can post a notification, do long-running background work, send intents to other services, or send a broadcast intent"
class LocationNotificationService: IntentService("Location-notification") {

    private val fences = HashMap<String, Geofence>().also {
        it["test_fence"] = Geofence.Builder()
            .setRequestId("test_fence")
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setCircularRegion(59.916044, 10.760100, 200f)
            .setExpirationDuration(29999)
            .build()

        //NOTE: 59.916044, 10.760100 -> SKOLE
    }

    private lateinit var geofencingClient: GeofencingClient
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, LocationNotificationService::class.java)
        PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    override fun onCreate() {
        super.onCreate()

        geofencingClient = GeofencingClient(this)
    }



    override fun onHandleIntent(intent: Intent?) {

        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        
        if (checkPermission()) {

            geofencingClient.addGeofences(getGeofencingRequest(), geofencePendingIntent)?.run {
                addOnSuccessListener {

                    sendNotification("added geofence")
                }
                addOnFailureListener {
                    // Failed to add geofences
                    // ...
                    println("failed to set geofence")
                }
            }
        }
    }



    private fun getGeofencingRequest(): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL)// send varsel hvis bruker oppholder seg en liten stund i GEOFENCE
            addGeofences(fences.values.toList())
        }.build()
    }

    private fun sendNotification(message: String) {

        //val notificationManager = NotificationManagerCompat.from(this)


                                            //TODO: pass information about which list
        val notificaionIntent = MainActivity.catchNotification(this)
        val stackBuilder = TaskStackBuilder.create(this).apply {
            addParentStack(MainActivity::class.java)
            addNextIntent(notificaionIntent)
        }



       // val notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        /*
       val notification = NotificationCompat.Builder(this)
           .setColor(Color.DKGRAY)
           .setSmallIcon(R.drawable.ic_launcher_foreground)
           .setContentTitle("Notification from Modulist")
           .setContentText("An alert about packing")
           .setPriority(NotificationCompat.PRIORITY_DEFAULT)
           .setContentIntent(notificationPendingIntent)
           .setDefaults(NotificationCompat.DEFAULT_ALL)
           .build()

       */

        val notificationManager = NotificationManagerCompat.from(this)

        val notification = NotificationCompat.Builder(this, App.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notification content title")
            .setContentText(message)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun checkPermission(): Boolean = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

/*
    private val fences = HashMap<String, Geofence>()


    private val locationCallback = object: LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {

            val location = locationResult.lastLocation
            sendNotification("location: ${location.altitude} ${location.latitude}")
        }
    }

    private lateinit var geofenceClient: GeofencingClient

    private val locationRequest = LocationRequest().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 300_000 //5 min
        fastestInterval = 180_000 // 3 min
    }

    override fun onBind(intent: Intent): IBinder? {

        return null
    }

    override fun onCreate() {

        if (checkPermission()) {

            val locationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
            locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

            geofenceClient = LocationServices.getGeofencingClient(this)
            fences.put("testfence", Geofence.Builder()
                .setRequestId("some fence")
                .setCircularRegion(
                    2.0, 2.0, 200f
                )
                .setExpirationDuration(199999)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build()
            )

        }
        else
            // no permission, wont't run
            stopSelf()

    }

    private fun getGeofencingRequest(): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(fences.values.toList())
        }.build()
    }

    private fun sendNotification(message: String) {

        val notificationManager = NotificationManagerCompat.from(this)

        val notification = NotificationCompat.Builder(this, App.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Some tititle").setContentText(message)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }

    //NOTE: location are not classified as dangerous. Therefore, it is not requried to check for it.
    // However, notificaions is PROTECTION_NORMAL
    private fun checkPermission(): Boolean = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        */