package org.olaven.modulist.task

import android.app.Application
import org.olaven.modulist.database.entity.ModuleList

class GetModuleListsTask(application: Application): CustomTask<GetModuleListsTask.DTO, Unit, List<ModuleList>>(application) {

    class DTO

    override fun doInBackground(vararg DTOs: DTO?): List<ModuleList> =
            moduleListModel.allModuleLists
}