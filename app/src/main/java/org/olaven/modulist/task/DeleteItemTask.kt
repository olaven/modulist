package org.olaven.modulist.task

import android.app.Application
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList

class DeleteItemTask(application: Application): CustomTask<DeleteItemTask.DTO, Unit, Unit>(application) {

    class DTO(val item: Item, val moduleList: ModuleList)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let {dto ->

                /*
                 * Recursively check lists downward in hierarchy.
                 * If the item is present, delete its first occurence (as items with same name may exist
                 */
                recursiveDelete(dto.item, dto.moduleList.id!!)
            }
        }
    }

    private fun recursiveDelete(item: Item, parentId: Long) {

        itemModel.delete(item)
        val relations = listRelationModel.getByParentId(parentId)
        relations.forEach { relation ->

            val items = itemModel.getbyModuleListId(relation.child!!)
            items
                .firstOrNull { it.name == item.name }
                ?.let {
                    recursiveDelete(it, relation.child!!)
                }
        }
    }
}