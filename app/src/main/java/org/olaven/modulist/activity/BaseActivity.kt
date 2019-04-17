package org.olaven.modulist.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import org.olaven.modulist.App
import org.olaven.modulist.R
import org.olaven.modulist.dialog.CameraRationaleDialog
import org.olaven.modulist.dialog.CustomDialog
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
        checkPermissionForLocation()
        checkPermissionForCamera()
        super.onCreate(savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

            App.REQUEST_ACCESS_FINE_LOCATION -> {

                val granted = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)

                if (granted)
                    serviceIntents.forEach { startService(it) }
                else
                    serviceIntents.forEach { stopService(it) }
            }
        }
    }


    private fun checkPermissionForLocation(): Boolean =

        // NOTE: do not have to check for version >= 25, as it always will be
        if (applicationContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            serviceIntents.forEach { startService(it) }
            true
        } else {
            // Innhent tilgang
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                App.REQUEST_ACCESS_FINE_LOCATION)
            false
        }

    private fun checkPermissionForCamera() {

        if (applicationContext.checkSelfPermission(Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED) {
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {

            CameraRationaleDialog(this).show()
        }
        else {
            // Innhent tilgang
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
                App.REQUEST_ACCESS_CAMERA)
        }

    }


    private fun applyTheme() {

        val themeName = getThemeName()

        Toast.makeText(applicationContext, themeName, Toast.LENGTH_SHORT)

        when(themeName) {
            getString(R.string.menu_theme_default) -> setTheme(R.style.DefaultTheme)
            getString(R.string.menu_theme_fruit)   -> setTheme(R.style.FruitTheme)
            getString(R.string.menu_theme_cabin)   -> setTheme(R.style.CabinTheme)
        }
    }

    private fun getThemeName(): String? {
        val preferenceKey = getString(R.string.preferences_modulist_key)
        val themeKey = getString(R.string.theme_key)

        val preferences = getSharedPreferences(preferenceKey, Context.MODE_PRIVATE)
        val default = getString(R.string.menu_theme_default)

        val themeName = preferences.getString(themeKey, default)
        return themeName
    }
}