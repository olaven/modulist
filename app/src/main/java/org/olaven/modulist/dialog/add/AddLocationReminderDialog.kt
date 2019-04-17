package org.olaven.modulist.dialog.add

import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.dialog.CustomDialog

class AddLocationReminderDialog(activity: AppCompatActivity): CustomDialog(activity) {

    override fun show() {

        showCustomDialog("Remind to pack when arriving at...") {


        }
    }
}