package org.olaven.modulist.dialog

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.database.Models

class ClearDataDialog(activity: AppCompatActivity): CustomDialog(activity) {

    override fun show() {

        showCustomDialog("Clear all data") {

            setPositiveButton("I am sure.") {

                activity.application?.let {
                    clearDatabase(it)
                }
            }

            setNegativeButton("Go back to safety!") {}
        }
    }

    private fun clearDatabase(application: Application) {

        Models.getListRelationModel(application).deleteAll()
        Models.getItemModel(application).deleteAll()
        Models.getModuleListModel(application).deleteAll()
    }
}