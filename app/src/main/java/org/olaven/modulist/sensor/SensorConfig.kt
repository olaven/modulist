package org.olaven.modulist.sensor

import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.net.Uri
import android.provider.Settings
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat.startActivity
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import org.olaven.modulist.Flashlight

/**
 * This file provides methods
 * for manipulating sensors.
 * The sensors are configured specifically for
 * the Modulist-application
 */
class SensorConfig(val activity: AppCompatActivity, val view: View) {

    val manager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val factory = SensorFactory(manager)

    val flashlight = Flashlight(activity)

    private val sensors = getSensors()


    fun startAll() {
        sensors.forEach { it.start() }
    }

    fun stopAll() {
        sensors.forEach { it.stop() }
    }


    private fun getSensors() = listOf(
        factory.light().apply {


            action = {

                if (flashlight.isAvailable()) {
                    flashlight.turnOn()
                    Snackbar.make(view, "It is dark. Flashlight is on.", Snackbar.LENGTH_INDEFINITE).apply {
                        setAction("Turn off.") {

                            flashlight.turnOff()
                        }
                    }.show()
                } else {
                    Snackbar.make(view, "This application does not have access to your camera", Snackbar.LENGTH_LONG).apply {
                        setAction("Allow camera usage") {
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${activity.packageName}"))
                                .apply {
                                    addCategory(Intent.CATEGORY_DEFAULT)
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }.also {
                                    activity.startActivity(it)
                                }
                        }
                    }.show()
                }
            }
        },
        factory.temperature().apply {

            action = {

                Snackbar.make(view, "It is cold. Maybe grab a jumper while you are at it?", Snackbar.LENGTH_LONG).apply {
                    setAction("Good idea!") {}
                }.show()
            }
        }
    )
}