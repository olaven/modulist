package org.olaven.modulist.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import org.olaven.modulist.database.entity.Item

@Dao
interface ItemDAO {

    @Insert
    fun insert(item: Item)

    @Query("select * from Item")
    fun getAll(): List<Item>

    @Query("select * from item")
    fun getAllLive(): LiveData<List<Item>>

    @Query("select * from Item where id = :id")
    fun getById(id: Int): Item

    @Delete
    fun delete(item: Item)

    @Query("delete from Item")
    fun deleteAll()

}