package org.olaven.modulist.dialog

import android.app.Activity
import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import org.olaven.modulist.R
import java.util.*

abstract class CustomDialog(protected val activity: AppCompatActivity) {

    //private val dialogs = Stack<AlertDialog.Builder>()
    private var dialog:AlertDialog.Builder? = null

    abstract fun show()

    protected fun setPositiveButton(text: String, action: () -> Unit) {

        dialog?.setPositiveButton(text) { _, _ ->
            action()
        }
    }

    protected fun setPositiveButton(action: () -> Unit) {

        val text = activity.getString(R.string.dialog_positive)
        setPositiveButton(text, action)
    }


    protected fun setNegativeButton(text: String, action: () -> Unit) {

        dialog?.setNegativeButton(text) { _, _ ->
            action()
        }
    }

    protected fun setNegativeButton(action: () -> Unit) {

        val text = activity.getString(R.string.dialog_negative)
        setNegativeButton(text, action)
    }

    protected fun showCustomDialog(title: String, applied: (dialogBuilder: AlertDialog.Builder) -> Unit) {

        AlertDialog.Builder(activity).apply {
            setTitle(title)


            dialog = this
            applied(this)
            show()
        }
    }
}