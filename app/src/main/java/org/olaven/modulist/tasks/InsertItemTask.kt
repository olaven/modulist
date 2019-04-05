package org.olaven.modulist.tasks

import android.app.Application
import org.olaven.modulist.database.entity.Item

class InsertItemTask(application: Application): CustomTask<InsertItemTask.DTO, Unit, Unit>(application) {

    class DTO(val item: Item)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let {dto ->

                itemModel.insert(dto.item)

                val childLists = listRelationModel.getByParentId(dto.item.moduleListId)
                childLists.forEach {

                    //NOTE: reassigning this is OK, as it has already been persisted a first time
                    dto.item.moduleListId = it.child!!
                    itemModel.insert(dto.item)
                }
            }
        }
    }
}