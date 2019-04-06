package org.olaven.modulist.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.google.android.gms.location.*
import org.olaven.modulist.App
import org.olaven.modulist.R
import kotlin.random.Random


class LocationNotificationService : Service() {

    private val locationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
    private val locationCallback = object: LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {

            val location = locationResult.lastLocation
            sendNotification("location: ${location.altitude} ${location.latitude}")
        }
    }

    private val locationRequest = LocationRequest().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 300_000 //5 min
        fastestInterval = 180_000 // 3 min
    }

    override fun onBind(intent: Intent): IBinder? {

        return null
    }

    override fun onCreate() {

        if (checkPermission())
            locationProviderClient
                .requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        else
            // no permission, wont't run
            stopSelf()

    }

    private fun sendNotification(message: String) {

        val notificationManager = NotificationManagerCompat.from(this)

        val notification = NotificationCompat.Builder(this, App.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Some tititle").setContentText(message)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }

    //NOTE: notifications are not classified as dangerous. Therefore, it is not requried to check for it.
    // However, location is PROTECTION_NORMAL
    private fun checkPermission(): Boolean = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
}
