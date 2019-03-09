package org.olaven.modulist.database.model

import android.app.Application
import android.arch.lifecycle.LiveData
import org.olaven.modulist.database.AppDatabase
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.database.repository.ModuleListRepository


class ModuleListModel(application: Application): CommonModel<ModuleList>(application) {


    override val repository = ModuleListRepository(
        AppDatabase.getDatabase(application.applicationContext).moduleListDAO()
    )

    val allModuleListsLive: LiveData<List<ModuleList>> = repository.allModuleListsLive

    suspend fun getById(id: Int) =
        repository.getById(id)



}