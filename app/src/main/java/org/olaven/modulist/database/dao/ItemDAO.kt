package org.olaven.modulist.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import org.olaven.modulist.database.entity.Item

@Dao
interface ItemDAO: CommonDAO<Item> {

    @Query("select * from Item")
    fun getAll(): List<Item>

    @Query("select * from item")
    fun getAllItemsLive(): LiveData<List<Item>>

    @Query("select * from Item where id = :id")
    fun getById(id: Int): Item


    @Query("delete from Item")
    fun deleteAll()

}