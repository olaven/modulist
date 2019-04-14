package org.olaven.modulist.task

import android.app.Application
import org.olaven.modulist.database.entity.ModuleList

class GetParentsTask(application: Application): CustomTask<GetParentsTask.DTO, Unit, List<ModuleList>>(application) {

    class DTO(val moduleList: ModuleList)

    override fun doInBackground(vararg DTOs: DTO?): List<ModuleList> {

        DTOs.forEach {

            it?.let {dto ->

                val moduleList = dto.moduleList
                val relations = listRelationModel.getByChildId(moduleList.id!!)

                return relations.map {
                    moduleListModel.getById(it.parent!!)
                }
            }
        }

        return emptyList()
    }
}