package org.olaven.modulist.task

import android.app.Application
import org.olaven.modulist.database.entity.Item

class AddItemTask(application: Application): CustomTask<AddItemTask.DTO, Unit, Unit>(application) {

    class DTO(val item: Item)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let {dto ->

                recursiveAdding(dto.item, dto.item.moduleListId)
            }
        }
    }

    private fun recursiveAdding(item: Item, parentId: Long) {

        itemModel.insert(item)
        val relations = listRelationModel.getByParentId(parentId)
        relations.forEach { relation ->

            // do not add item twice in the case of inheriting from multiple parents:
            val alreadyAdded = itemModel.getByModuduleListId(relation.child!!)
                .filter { it.name == item.name }
                .isNotEmpty()

            if (!alreadyAdded) {
                val moduleList = moduleListModel.getById(relation.child!!)
                item.moduleListId = moduleList.id!!
                recursiveAdding(item, moduleList.id!!)
            }
        }
    }
}