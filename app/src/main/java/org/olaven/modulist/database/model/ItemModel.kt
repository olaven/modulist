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


    val allItems = repository.allItemsLive

    suspend fun getByModuleListId(id: Int) =
        repository.getByModuleListId(id)

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

}