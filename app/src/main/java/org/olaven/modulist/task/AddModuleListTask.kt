package org.olaven.modulist.task

import android.app.Application
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ListRelation
import org.olaven.modulist.database.entity.ModuleList


class AddModuleListTask(application: Application): CustomTask<AddModuleListTask.DTO, Any, Unit>(application) {

    class DTO(val moduleList: ModuleList, val selectedParents: List<ModuleList>)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let {dto ->

                // persist the list
                val id = moduleListModel.insertForId(dto.moduleList)

                /*
                 * For each parent
                 * 1: fetch items and add them to current one (filter out duplicates)
                 * 2: store relation in database as ListRelation
                 */

                //1:
                dto.selectedParents
                    .flatMap { itemModel.getbyModuleListId(it.id!!) }
                    .distinctBy { it.name }
                    .forEach {
                        val copy = Item(it.name, it.done, it.dayDistribution, id)
                        itemModel.insert(copy)
                    }

                //2:
                dto.selectedParents.forEach {parent ->
                    val listRelation = ListRelation(id, parent.id)
                    listRelationModel.insert(listRelation)
                }
            }
        }
    }
}