package org.olaven.modulist.database.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import org.olaven.modulist.database.dao.ItemDAO
import org.olaven.modulist.database.entity.Item

class ItemRepository(private val itemDAO: ItemDAO): CommonRepository<Item>(itemDAO) {

    val allItemsLive: LiveData<List<Item>> = itemDAO.getAllItemsLive()

    @WorkerThread
    suspend fun getByid(id: Long) =
        itemDAO.getById(id)

    @WorkerThread
    suspend fun getByModuleListId(id: Long): LiveData<List<Item>> =
            itemDAO.getByModuleListId(id)

    fun deleteAll() =
            itemDAO.deleteAll()

}