package org.olaven.modulist.dialog

import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.R
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.task.DeleteItemTask

class DeleteItemDialog(private val item: Item, val moduleList: ModuleList, activity: AppCompatActivity): CustomDialog(activity) {

    override fun show() {

        showCustomDialog(activity.getString(R.string.dialog_delete_item)) {

            setPositiveButton {

                val dto = DeleteItemTask.DTO(item, moduleList)
                DeleteItemTask(activity.application)
                    .execute(dto)
            }
            setNegativeButton {  }
        }
    }
}