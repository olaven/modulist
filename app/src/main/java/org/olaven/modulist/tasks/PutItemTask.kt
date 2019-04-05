package org.olaven.modulist.tasks

import android.app.Application
import org.olaven.modulist.database.entity.Item
import java.lang.Exception

/**
 * Adds item to db, associated with list as well as child-lists.
 * If item is already persisted, it will replace the existing one.
 * (working like .put in a map)
 */
class PutItemTask(application: Application): CustomTask<PutItemTask.DTO, Unit, Unit>(application) {

    class DTO(val item: Item)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let {dto ->

                if (itemExists(dto.item)) {

                    itemModel.update(dto.item)
                    return
                } else {

                    itemModel.insert(dto.item)
                }

                val childLists = listRelationModel.getByParentId(dto.item.moduleListId)
                childLists.forEach {

                    //NOTE: reassigning this is OK, as it has already been persisted a first time
                    dto.item.moduleListId = it.child!!
                    itemModel.insert(dto.item)
                }
            }
        }
    }

    /**
     * If the item is present, updates it.
     * Else, adds it
     */
    private fun itemExists(item: Item): Boolean =
        try {
            itemModel.getById(item.id!!)
            true
        } catch (e: Exception) {
            false
        }

}