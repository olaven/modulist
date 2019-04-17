package org.olaven.modulist.dialog.update

import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.dialog.CustomDialog

abstract class UpdateModuleListDialog(protected val moduleList: ModuleList, activity: AppCompatActivity): CustomDialog(activity) {

    protected fun update() {

        Models.getModuleListModel(activity.application)
            .update(moduleList)
    }
}