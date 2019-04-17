package org.olaven.modulist.dialog

import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
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