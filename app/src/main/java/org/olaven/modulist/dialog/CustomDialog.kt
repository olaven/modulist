package org.olaven.modulist.dialog

import android.app.Activity
import android.app.AlertDialog

abstract class CustomDialog(activity: Activity) {

    abstract fun show()

    protected fun createCustomDialog(context: Activity, title: String, applied: (dialogBuilder: AlertDialog.Builder) -> Unit) {

        AlertDialog.Builder(context).apply {
            setTitle(title)
            applied(this)
            show()
        }
    }
}