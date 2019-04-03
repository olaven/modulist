package org.olaven.modulist.dialog

import android.app.Activity
import android.app.AlertDialog

abstract class CustomDialog(protected val activity: Activity) {

    abstract fun show()

    protected fun displayCustomDialog(title: String, applied: (dialogBuilder: AlertDialog.Builder) -> Unit) {

        AlertDialog.Builder(activity).apply {
            setTitle(title)
            applied(this)
            show()
        }
    }
}