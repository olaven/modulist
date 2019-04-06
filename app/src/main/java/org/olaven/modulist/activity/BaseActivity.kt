package org.olaven.modulist.activity

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import org.olaven.modulist.App
import org.olaven.modulist.R
import org.olaven.modulist.service.LocationNotificationService

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        applyTheme()
        checkPermissionForLocation(applicationContext)
        super.onCreate(savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

            App.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                val granted = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                val intent = Intent(this, LocationNotificationService::class.java)

                if (granted)
                    startLocationService()
                else
                    stopService(intent)
            }
        }
    }


    private fun checkPermissionForLocation(context: Context): Boolean =

        // NOTE: do not have to check for version >= 25, as it always will be
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            startLocationService()
            true
        } else {
            // Innhent tilgang
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                App.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            false
        }

    private fun startLocationService() {

        val intent = Intent(this, LocationNotificationService::class.java)
        startService(intent)
    }


    private fun applyTheme() {

        val themeName = getThemeName()

        Toast.makeText(applicationContext, themeName, Toast.LENGTH_SHORT)

        when(themeName) {
            getString(R.string.menu_theme_default) -> setTheme(R.style.DefaultTheme)
            getString(R.string.menu_theme_dark)    -> setTheme(R.style.DarkTheme)
            getString(R.string.menu_theme_fruit)   -> setTheme(R.style.FruitTheme)
            getString(R.string.menu_theme_cabin)   -> setTheme(R.style.CabinTheme)
        }
    }

    private fun getThemeName(): String? {
        val preferenceKey = getString(R.string.preferences_modulist)
        val themeKey = getString(R.string.theme_key)

        val preferences = getSharedPreferences(preferenceKey, Context.MODE_PRIVATE)
        val default = getString(R.string.menu_theme_default)

        val themeName = preferences.getString(themeKey, default)
        return themeName
    }
}