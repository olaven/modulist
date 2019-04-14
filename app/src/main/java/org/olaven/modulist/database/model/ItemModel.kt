package org.olaven.modulist.database.model

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.olaven.modulist.database.AppDatabase
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.repository.ItemRepository

class ItemModel(application: Application): CommonModel<Item>(application) {


    override val repository = ItemRepository(
        AppDatabase.getDatabase(application.applicationContext).itemDAO()
    )

    fun getById(id: Long) =
            repository.getById(id)

    fun getByModuleListIdLive(id: Long) =
        repository.getByModuleListIdLive(id)

    fun getByModuduleListId(id: Long): List<Item> =
        repository.getByModuleListId(id)

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

}