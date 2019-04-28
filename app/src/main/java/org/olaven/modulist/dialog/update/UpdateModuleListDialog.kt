package org.olaven.modulist.dialog.update

import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.database.ModelFactory
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.dialog.CustomDialog

abstract class UpdateModuleListDialog(protected val moduleList: ModuleList, activity: AppCompatActivity): CustomDialog(activity) {

    protected fun update() {

        ModelFactory.getModuleListModel(activity.application)
            .update(moduleList)
    }
}