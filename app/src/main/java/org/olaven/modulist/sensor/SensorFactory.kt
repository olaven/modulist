package org.olaven.modulist.sensor

import android.hardware.SensorManager

class SensorFactory(val manager: SensorManager) {

    fun light() = LightSensor(manager)
    fun temperature() = TemperatureSensor(manager)
}