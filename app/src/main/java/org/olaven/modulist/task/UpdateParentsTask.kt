package org.olaven.modulist.task

import android.app.Application
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList


class UpdateParentsTask(application: Application): CustomTask<UpdateParentsTask.DTO, Unit, Unit>(application) {

    class DTO(val moduleList: ModuleList, val parents: List<ModuleList>)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let {dto ->

                // delete all existing
                itemModel.getbyModuleListId(dto.moduleList.id!!).forEach {
                    itemModel.delete(it)
                }

                // get all items
                val items = mutableListOf<Item>()
                dto.parents.forEach {

                    val fromParent = itemModel.getbyModuleListId(it.id!!)
                    items.addAll(fromParent)
                }

                // remove with same name, and insert copies
                items.distinctBy { it.name }
                    .map { Item(it.name, it.done, it.dayDistribution, dto.moduleList.id!!) } //make copies
                    .forEach { itemModel.insert(it) }
            }
        }
    }
}