package org.olaven.modulist.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import org.olaven.modulist.database.dao.ItemDAO
import org.olaven.modulist.database.entity.Item

class ItemRepository(private val itemDAO: ItemDAO): CommonRepository<Item>(itemDAO) {

    val allItemsLive: LiveData<List<Item>> = itemDAO.getAllItemsLive()



    @WorkerThread
    fun getByIdLive(id: Long) =
        itemDAO.getByIdLive(id)

    @WorkerThread
    fun getByModuleListIdLive(id: Long) =
            itemDAO.getByModuleListIdLive(id)

    fun getByModuleListId(id: Long): List<Item> =
            itemDAO.getByModuleListId(id)

    fun deleteAll() =
            itemDAO.deleteAll()

    fun getById(id: Long): Item =
            itemDAO.getById(id)

}