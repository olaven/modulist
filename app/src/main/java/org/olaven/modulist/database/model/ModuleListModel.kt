package org.olaven.modulist.database.model

import android.app.Application
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.olaven.modulist.database.AppDatabase
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.database.repository.ModuleListRepository


class ModuleListModel(application: Application): CommonModel<ModuleList>(application) {


    override val repository = ModuleListRepository(
        AppDatabase.getDatabase(application.applicationContext).moduleListDAO()
    )

    val allModuleListsLive: LiveData<List<ModuleList>> =
        repository.allModuleListsLive

    fun getByIdLive(id: Long) =
            repository.getByIdLive(id)

    fun getById(id: Long) =
            repository.getById(id)

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}