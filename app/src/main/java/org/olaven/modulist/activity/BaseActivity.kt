package org.olaven.modulist.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import org.olaven.modulist.App
import org.olaven.modulist.R
import org.olaven.modulist.service.GeofenceService
import org.olaven.modulist.service.NotificationService

open class BaseActivity: AppCompatActivity() {

    private lateinit var serviceIntents: List<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {

        serviceIntents = listOf(
            Intent(this, GeofenceService::class.java),
            Intent(this, NotificationService::class.java)
        )

        applyTheme()
        checkPermissionForLocation(applicationContext)
        super.onCreate(savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

            App.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                val granted = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)

                if (granted)
                    serviceIntents.forEach { startService(it) }
                else
                    serviceIntents.forEach { stopService(it) }
            }
        }
    }


    private fun checkPermissionForLocation(context: Context): Boolean =

        // NOTE: do not have to check for version >= 25, as it always will be
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            serviceIntents.forEach { startService(it) }
            true
        } else {
            // Innhent tilgang
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                App.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            false
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