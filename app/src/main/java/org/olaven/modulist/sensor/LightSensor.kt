package org.olaven.modulist.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager

class LightSensor(manager: SensorManager): CustomSensor(manager) {

    override val sensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT)

    override fun eventCheck(event: SensorEvent): Boolean {

        val light = event.values[0]
        println("sensor: light $light")
        return light < 50
    }
}