package org.olaven.modulist.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import org.olaven.modulist.database.entity.Item

@Dao
abstract class ItemDAO: CommonDAO<Item> {

    @Query("select * from Item")
    abstract fun getAllItemsLive(): LiveData<List<Item>>

    @Query("select * from Item where id = :id")
    abstract fun getById(id: Long): LiveData<Item>

    @Query("select * from Item where moduleListId = :id")
    abstract fun getByModuleListId(id: Long): LiveData<List<Item>>

    @Query("delete from Item")
    abstract fun deleteAll()

}