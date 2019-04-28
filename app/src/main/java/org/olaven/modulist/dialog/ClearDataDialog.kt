package org.olaven.modulist.dialog

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.database.ModelFactory

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

        ModelFactory.getListRelationModel(application).deleteAll()
        ModelFactory.getItemModel(application).deleteAll()
        ModelFactory.getModuleListModel(application).deleteAll()
    }
}