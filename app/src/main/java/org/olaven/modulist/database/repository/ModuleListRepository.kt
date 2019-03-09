package org.olaven.modulist.database.repository

import android.arch.lifecycle.LiveData
import org.olaven.modulist.database.dao.ModuleListDAO
import org.olaven.modulist.database.entity.ModuleList

class ModuleListRepository(private val moduleListDAO: ModuleListDAO) : CommonRepository<ModuleList>(moduleListDAO) {

    val allModuleListsLive: LiveData<List<ModuleList>> = moduleListDAO.getAllModuleListsLive()

    suspend fun getById(id: Int) =
        moduleListDAO.getById(id)
}
