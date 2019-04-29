package org.olaven.modulist.sensor

import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.olaven.modulist.Flashlight
import org.olaven.modulist.R

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
                    Snackbar.make(view, activity.getString(R.string.snackbar_sensor_it_is_dark), Snackbar.LENGTH_INDEFINITE).apply {
                        setAction(activity.getString(R.string.snackbar_sensor_turn_light_off)) {

                            flashlight.turnOff()
                        }
                    }.show()
                } else {
                    Snackbar.make(view, activity.getString(R.string.snackbar_sensor_lacking_access), Snackbar.LENGTH_LONG).apply {
                        setAction(activity.getString(R.string.snackbar_sensor_allow_access)) {
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

                Snackbar.make(view, activity.getString(R.string.snackbar_sensor_it_is_cold), Snackbar.LENGTH_LONG).apply {
                    setAction(activity.getString(R.string.snackbar_sensor_cold_positive)) {}
                }.show()
            }
        }
    )
}