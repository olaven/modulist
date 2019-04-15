package org.olaven.modulist.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

abstract class CustomSensor(private val manager: SensorManager): SensorEventListener {

    abstract val sensor: Sensor
    var action: () -> Unit = {}

    fun start(){

        if (sensor != null) // in case sensor is not present on device
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stop() {
        manager.unregisterListener(this)
    }


    // triggers .action if event chech passes
    abstract fun eventCheck(event: SensorEvent): Boolean

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    override fun onSensorChanged(event: SensorEvent?) {

        event?.let {

            //sensor is not present on device
            if (eventCheck(it))
                action()
        }
    }
}