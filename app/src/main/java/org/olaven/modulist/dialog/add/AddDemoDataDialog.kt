package org.olaven.modulist.dialog.add

import android.app.Application
import android.support.v7.app.AppCompatActivity
import org.olaven.modulist.database.Models
import org.olaven.modulist.dialog.CustomDialog
import org.olaven.modulist.task.add.AddDemoDataTask

class AddDemoDataDialog(activity: AppCompatActivity): CustomDialog(activity) {

    override fun show() {

        showCustomDialog("Insert demo data") {

            it.setMessage("Demo data will permanently REPLACE your data")

            setPositiveButton("I am sure") {

                insertDemoData(activity.application)
            }

            setNegativeButton("Take me back to safety") {}
        }
    }

    private fun insertDemoData(application: Application) {

        clearDatabase(application)

        AddDemoDataTask(application).execute()
    }

    private fun clearDatabase(application: Application) {

        Models.getListRelationModel(application).deleteAll()
        Models.getItemModel(application).deleteAll()
        Models.getModuleListModel(application).deleteAll()
    }
}