package org.olaven.modulist.database.repository

import androidx.annotation.WorkerThread
import org.olaven.modulist.database.dao.ModuleListDAO
import org.olaven.modulist.database.entity.ModuleList

class ModuleListRepository(private val moduleListDAO: ModuleListDAO) : CommonRepository<ModuleList>(moduleListDAO) {


    @WorkerThread
    fun getAllModuleListsLive() =
        moduleListDAO.getAllModuleListsLive()


    @WorkerThread
    fun getAllModuleLists() =
        moduleListDAO.getAllModuleLists()


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
