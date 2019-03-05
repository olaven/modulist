package org.olaven.modulist.database.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import org.olaven.modulist.database.dao.ItemDAO
import org.olaven.modulist.database.entity.Item

class ItemRepository(private val itemDAO: ItemDAO) {

    val allItemsLive: LiveData<List<Item>> =
        itemDAO.getAllLive()

    // val allItems... ikke observator

    @WorkerThread
    suspend fun insert(item: Item) =
        itemDAO.insert(item)

    @WorkerThread
    suspend fun deleteAll() =
        itemDAO.deleteAll()
}