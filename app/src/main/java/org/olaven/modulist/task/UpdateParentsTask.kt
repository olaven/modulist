package org.olaven.modulist.task

import android.app.Application
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ListRelation
import org.olaven.modulist.database.entity.ModuleList


class UpdateParentsTask(application: Application): CustomTask<UpdateParentsTask.DTO, Unit, Unit>(application) {

    class DTO(val moduleList: ModuleList, val parents: List<ModuleList>)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let {dto ->


                println(dto.parents)
               /* val parentItems = dto.parents.flatMap {
                    itemModel.getByModuduleListId(it.id!!)
                }.map { it.name }*/

                val parentItems = mutableListOf<String>()
                listRelationModel.getByChildId(dto.moduleList.id!!).forEach {

                    val items = itemModel.getByModuduleListId(it.parent!!)
                    parentItems.addAll(items.map { it.name })
                }

                val notFromParents = itemModel.getByModuduleListId(dto.moduleList.id!!)
                    .filterNot { parentItems.contains(it.name) }
                // delete all existing items and relations to the list
                itemModel.getByModuduleListId(dto.moduleList.id!!).forEach {
                    itemModel.delete(it)
                }
                listRelationModel.getByChildId(dto.moduleList.id!!).forEach {
                    listRelationModel.delete(it)
                }

                // get all items
                val items = notFromParents.toMutableList()
                dto.parents.forEach {

                    // add items from parent
                    val fromParent = itemModel.getByModuduleListId(it.id!!)
                    items.addAll(fromParent)

                    // add a relation
                    listRelationModel.insert(ListRelation(dto.moduleList.id!!, it.id!!))
                }

                // remove with same name, and insert copies
                items.distinctBy { it.name }
                    .map { Item(it.name, it.done, it.dayDistribution, dto.moduleList.id!!) } //make copies
                    .forEach { itemModel.insert(it) }
            }
        }
    }
}