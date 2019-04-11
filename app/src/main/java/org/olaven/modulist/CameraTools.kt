package org.olaven.modulist

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.camera2.CameraManager
import android.support.v7.app.AppCompatActivity
import kotlin.random.Random

/**
 * This class is for launching a camera.
 * To access the picture taken, override
 * onActivityResult and listen for CameraTools.CAMERA_REQUEST_CODE
 */
class CameraTools(val activity: Activity) {

    val context: Context = activity.applicationContext

    fun isPresent(): Boolean {

        return context
            .packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    fun takePicture() {

        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(intent, App.REQUEST_CODE_CAMERA)
    }

    fun getBitMap(data: Intent): Bitmap {

        return data.extras.get("data") as Bitmap
    }
}