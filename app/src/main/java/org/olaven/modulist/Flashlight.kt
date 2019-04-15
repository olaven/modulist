package org.olaven.modulist

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager


class Flashlight(val activity: Activity) {

    val cameraManager = activity.getSystemService(Service.CAMERA_SERVICE) as CameraManager
    val camera = cameraManager.getCameraIdList()[0]


    fun isAvailable() =
        isPresent() && isAllowed()
    fun turnOn() =
            setTorchOn(true)

    fun turnOff() =
        setTorchOn(false)

    // CHECKING AVAILABILITY:
    private fun isPresent() =
        activity.applicationContext
            .packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA)

    private fun isAllowed() =
        activity
            .checkSelfPermission(Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED

    private fun setTorchOn(on: Boolean) =
        cameraManager.setTorchMode(camera, on)

}