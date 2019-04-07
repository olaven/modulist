package org.olaven.modulist.dialog

import android.support.v7.app.AppCompatActivity
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.task.DeleteItemTask

class DeleteItemDialog(private val item: Item, val moduleList: ModuleList, activity: AppCompatActivity): CustomDialog(activity) {

    override fun show() {

        showCustomDialog("This will delete the item from all child-lists as well") {

            setPositiveButton {

                val dto = DeleteItemTask.DTO(item, moduleList)
                DeleteItemTask(activity.application)
                    .execute(dto)
            }
            setNegativeButton {  }
        }
    }
}