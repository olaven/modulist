package org.olaven.modulist

import android.app.AlertDialog
import android.content.Context

fun createCustomDialog(context: Context, title: String, applied: (dialogBuilder: AlertDialog.Builder) -> Unit) {

    AlertDialog.Builder(context).apply {
        setTitle(title)
        applied(this)
        show()
    }
}