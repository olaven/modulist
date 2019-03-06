package org.olaven.modulist.database.model

import android.app.Application
import android.arch.lifecycle.LiveData
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

    suspend fun getByModuleListId(id: Long) =
        repository.getByModuleListId(id)


}