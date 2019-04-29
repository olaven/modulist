package org.olaven.modulist.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager

class TemperatureSensor(manager: SensorManager): CustomSensor(manager) {

    override val sensor = manager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

    override fun eventCheck(event: SensorEvent): Boolean {

        val temperature = event.values[0]
        return temperature < 5
    }
}