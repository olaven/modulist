package org.olaven.modulist.dialog.add

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.R
import org.olaven.modulist.database.ModelFactory
import org.olaven.modulist.dialog.CustomDialog
import org.olaven.modulist.task.add.AddDemoDataTask

class AddDemoDataDialog(activity: AppCompatActivity): CustomDialog(activity) {

    override fun show() {

        showCustomDialog(activity.getString(R.string.dialog_add_demo_data_title)) {

            it.setMessage(activity.getString(R.string.dialog_add_demo_data_message))

            setPositiveButton(activity.getString(R.string.dialog_positive_serious)) {

                insertDemoData(activity.application)
            }

            setNegativeButton(activity.getString(R.string.dialog_negative_serious)) {}
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