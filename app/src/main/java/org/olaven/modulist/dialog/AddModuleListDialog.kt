package org.olaven.modulist.dialog

import android.app.Activity
import android.content.DialogInterface
import android.widget.EditText
import org.olaven.modulist.database.entity.ModuleList

class AddModuleListDialog(private val inheritanceOptions: List<ModuleList>, activity: Activity): CustomDialog(activity) {

    override fun show() {

        val names = inheritanceOptions.map { it.name }.map { it as CharSequence }.toTypedArray()
        val checked = inheritanceOptions.map { false }.toBooleanArray()

        createCustomDialog("Add a list called..") {

            val view = EditText(activity)
            it.setView(view)
            it.setPositiveButton("Next") { _, _ ->

                createCustomDialog("What lists do you want to inherit from?") {

                    it.setMultiChoiceItems(names, checked) {_, _, _ ->


                    }
                }
            }
        }
    }
}