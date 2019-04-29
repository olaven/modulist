package org.olaven.modulist.dialog

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.R
import org.olaven.modulist.database.ModelFactory

class ClearDataDialog(activity: AppCompatActivity): CustomDialog(activity) {

    override fun show() {

        showCustomDialog(activity.getString(R.string.dialog_clear_data)) {

            setPositiveButton(activity.getString(R.string.dialog_positive_serious)) {

                activity.application?.let {
                    clearDatabase(it)
                }
            }

            setNegativeButton(activity.getString(R.string.dialog_negative_serious)) {}
        }
    }

    private fun clearDatabase(application: Application) {

        ModelFactory.getListRelationModel(application).deleteAll()
        ModelFactory.getItemModel(application).deleteAll()
        ModelFactory.getModuleListModel(application).deleteAll()
    }
}