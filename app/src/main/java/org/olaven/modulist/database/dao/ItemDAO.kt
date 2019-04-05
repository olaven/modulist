package org.olaven.modulist.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import org.olaven.modulist.database.entity.Item

@Dao
interface ItemDAO: CommonDAO<Item> {

    @Query("select * from Item where id = :id")
    fun getById(id: Long): Item

    @Query("select * from Item")
    fun getAllItemsLive(): LiveData<List<Item>>

    @Query("select * from Item where id = :id")
    fun getByIdLive(id: Long): LiveData<Item>

    @Query("select * from Item where moduleListId = :id")
    fun getByModuleListIdLive(id: Long): LiveData<List<Item>>

    @Query("select * from Item where moduleListId = :id")
    fun getByModuleListId(id: Long): List<Item>

    @Query("delete from Item")
    fun deleteAll()

}