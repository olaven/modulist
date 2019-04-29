package org.olaven.modulist.dialog

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.olaven.modulist.App
import org.olaven.modulist.R

class CameraRationaleDialog(activity: AppCompatActivity): CustomDialog(activity) {

    override fun show() {

        showCustomDialog(activity.getString(R.string.dialog_camera_permission_title)) {

            it.setMessage(activity.getString(R.string.dialog_camera_permission_message))
            setPositiveButton {
                ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA),
                    App.REQUEST_ACCESS_CAMERA)
            }
        }
    }
}