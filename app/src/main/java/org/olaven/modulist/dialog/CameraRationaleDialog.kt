package org.olaven.modulist.dialog

import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import org.olaven.modulist.App

class CameraRationaleDialog(activity: AppCompatActivity): CustomDialog(activity) {

    override fun show() {

        showCustomDialog("Usage of camera") {

            it.setMessage("The camera-flash is only used as a flashlight.")
            setPositiveButton {
                ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA),
                    App.REQUEST_ACCESS_CAMERA)
            }
        }
    }
}