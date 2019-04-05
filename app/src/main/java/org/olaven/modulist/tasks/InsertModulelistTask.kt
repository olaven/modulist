package org.olaven.modulist.tasks

import android.app.Application
import android.arch.lifecycle.Observer
import android.os.AsyncTask
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ListRelation
import org.olaven.modulist.database.entity.ModuleList


class InsertModulelistTask(application: Application): CustomTask<InsertModulelistTask.DTO, Any, Unit>(application) {

    class DTO(val moduleList: ModuleList, val inheritanceOptions: List<ModuleList>)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let {dto ->

                // persist the list
                val moduleList = ModuleList(dto.moduleList.name, dto.moduleList.color)
                val id = moduleListModel.insertForId(moduleList)


                /*
                 * For each parent
                 * 1: fetch items and add them to current one
                 * 2: store relation in database as ListRelation
                 */
                dto.inheritanceOptions.forEach {parent ->

                    //1:
                    val parentItems = itemModel.getbyModuleListId(parent.id!!)
                    parentItems.forEach {
                        val copy = Item(it.name, it.done, it.dayDistribution, id)
                    }

                    //2:
                    val listRelation = ListRelation(id, parent.id)
                    listRelationModel.insert(listRelation)
                }
            }
        }
    }
}