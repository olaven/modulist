package org.olaven.modulist.database.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import org.olaven.modulist.database.dao.ItemDAO
import org.olaven.modulist.database.entity.Item

class ItemRepository(private val itemDAO: ItemDAO): CommonRepository<Item>(itemDAO) {

    val allItemsLive: LiveData<List<Item>> = itemDAO.getAllItemsLive()

    @WorkerThread
    suspend fun getByModuleListId(id: Int): LiveData<List<Item>> =
            itemDAO.getByModuleListId(id)

    override fun deleteAll() =
            itemDAO.deleteAll()

}