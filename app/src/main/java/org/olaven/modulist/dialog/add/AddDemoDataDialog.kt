package org.olaven.modulist.dialog.add

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.database.ModelFactory
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

        ModelFactory.getListRelationModel(application).deleteAll()
        ModelFactory.getItemModel(application).deleteAll()
        ModelFactory.getModuleListModel(application).deleteAll()
    }
}