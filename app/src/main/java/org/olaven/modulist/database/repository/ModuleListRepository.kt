package org.olaven.modulist.database.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import org.olaven.modulist.database.dao.ModuleListDAO
import org.olaven.modulist.database.entity.ModuleList

class ModuleListRepository(private val moduleListDAO: ModuleListDAO) : CommonRepository<ModuleList>(moduleListDAO) {

    val allModuleListsLive: LiveData<List<ModuleList>> = moduleListDAO.getAllModuleListsLive()

    @WorkerThread
    fun getByIdLive(id: Long) =
        moduleListDAO.getByIdLive(id)

    fun getById(id: Long) =
        moduleListDAO.getById(id)

    fun deleteAll() {
        moduleListDAO.deleteAll()
    }

    @WorkerThread
    fun getByName(name: String) =
            moduleListDAO.getByName(name)
}
